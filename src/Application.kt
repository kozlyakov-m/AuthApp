import ExitCode.*
import java.text.ParseException
import java.text.SimpleDateFormat

class Application(args: Array<String>) {
    private val argHandler: ArgHandler = ArgHandler(args)
    private val authService = AuthService()

    fun run(): Int {
        if (argHandler.canTryAuthentication()) {
            val authenticationResult = startAuthentication()
            val authenticationCode = authenticationResult.first
            if (authenticationCode != SUCCESS) {
                return authenticationResult.first.value
            }

            val isAuthorization =
                argHandler.canTryAuthorization()
                        && authenticationCode == SUCCESS
            val currentUser = authenticationResult.second

            if (!isAuthorization || currentUser == null) {
                return authenticationCode.value
            }

            val authorizationResult = startAuthorization(currentUser)
            if (authorizationResult.first != SUCCESS)
                return authenticationResult.first.value


        }

        if (argHandler.canTryAccounting()) {
            val dateStartInp = argHandler.getArgument(ArgHandler.Arguments.DATE_START) ?: return INVALID_ACTIVITY.value
            val dateEndInp = argHandler.getArgument(ArgHandler.Arguments.DATE_END) ?: return INVALID_ACTIVITY.value
            val volumeInp = argHandler.getArgument(ArgHandler.Arguments.VOLUME) ?: return INVALID_ACTIVITY.value

            try {
                val dateStart = parseDate(dateStartInp)
                val dateEnd = parseDate(dateEndInp)
                val volume = volumeInp.toInt()

                if (dateStart.before(dateEnd) || volume < 1)
                    return INVALID_ACTIVITY.value

            } catch (e: Exception) {
                when (e) {
                    is NumberFormatException, is ParseException -> return INVALID_ACTIVITY.value
                    else -> throw e
                }
            }
        }

        printHelp()
        return HELP.value
    }

    private fun startAuthentication(): Pair<ExitCode, User?> {
        val login = argHandler.getArgument(ArgHandler.Arguments.LOGIN)
        val pass = argHandler.getArgument(ArgHandler.Arguments.PASSWORD)

        if (!validateLogin(login))
            return Pair(INVALID_LOGIN_FORMAT, null)

        if (pass == null || !validatePass(pass))
            return Pair(INVALID_PASSWORD, null)

        val currentUser = authService.getUserByLogin(login!!) ?: return Pair(UNKNOWN_LOGIN, null)

        if (!authService.verifyPass(pass, currentUser))
            return Pair(INVALID_PASSWORD, null)

        return Pair(SUCCESS, currentUser)
    }

    private fun startAuthorization(user: User): Pair<ExitCode, UsersResources?> {
        // TODO: должно приходить уже разыменованное
        val resource = argHandler.getArgument(ArgHandler.Arguments.RESOURCE)!!
        val roleInput = argHandler.getArgument(ArgHandler.Arguments.ROLE)!!

        if (!validateRole(roleInput)) {
            return Pair(UNKNOWN_ROLE, null)
        }
        val usersResource = UsersResources(resource, Role.valueOf(roleInput.toUpperCase()), user.login)
        val authorizationService = AuthorizationService(usersResource)

        return if (authorizationService.haveAccess()) {
            Pair(SUCCESS, authorizationService.usersResource)
        } else {
            Pair(NO_ACCESS, null)
        }
    }

    private fun printHelp() {
        println(
            """
            -h - напечатать эту справку
            -login <str> - логин пользователя, формат - [a-z]{10}
            -pas <st> - пароль, может быть любым, но как минимум 1 символ
            -role <str> - роль (READ, WRITE, READ)
            -res <str> - имя ресурса, полный путь заглавные буквы
            -ds <YYYY-MM-DD> - начальная дата
            -de <YYYY-MM-DD> - конечная дата
            -vol <int> - объём работы, натуральное число
            """.trimIndent()
        )
    }

    private fun validateLogin(login: String?): Boolean {
        return login != null && login.matches(Regex("[a-z]{1,10}"))
    }

    private fun validatePass(pass: String?) = pass != null && pass.isNotEmpty()

    private fun validateRole(role: String?) = listOf("READ", "WRITE", "EXECUTE").contains(role?.toUpperCase())

    private fun parseDate(date: String) = SimpleDateFormat("yyyy-MM-dd").parse(date)
}