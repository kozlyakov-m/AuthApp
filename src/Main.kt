import kotlin.system.exitProcess


fun main(args: Array<String>) {
    val argHandler = ArgHandler(args)
    val authService = AuthService()
    var authSuccessful = false

    if (argHandler.canTryAuth()) {
        val login = argHandler.getArgument(ArgHandler.Arguments.LOGIN)
        val pass = argHandler.getArgument(ArgHandler.Arguments.PASSWORD)

        if (!validateLogin(login))
            exitProcess(ExitCode.INVALID_LOGIN_FORMAT.value)

        if (pass == null || !validatePass(pass))
            exitProcess(ExitCode.INVALID_PASSWORD.value)

        val currentUser = authService.getUserByLogin(login!!)

        if (!authService.userExists(currentUser))
            exitProcess(ExitCode.UNKNOWN_LOGIN.value)

        if (!authService.verifyPass(pass, currentUser!!))
            exitProcess(ExitCode.INVALID_PASSWORD.value)

        authSuccessful = true
    }

    val authorizationSuccessful = false
    if (authSuccessful && argHandler.canTryAuthorization()) {

        val res = listOf(
            UsersResources("A", Role.READ, authService.getUserByLogin("sasha")!!),
            UsersResources("A.AA", Role.WRITE, authService.getUserByLogin("sasha")!!),
            UsersResources("A.AA.AAA", Role.EXECUTE, authService.getUserByLogin("sasha")!!),
            UsersResources("B", Role.EXECUTE, authService.getUserByLogin("admin")!!),
            UsersResources("A.B", Role.WRITE, authService.getUserByLogin("admin")!!),
            UsersResources("A.B", Role.WRITE, authService.getUserByLogin("sasha")!!),
            UsersResources("A.B.C", Role.READ, authService.getUserByLogin("admin")!!),
            UsersResources("A.B.C", Role.WRITE, authService.getUserByLogin("q")!!),
            UsersResources("A.B", Role.EXECUTE, authService.getUserByLogin("q")!!),
            UsersResources("B", Role.READ, authService.getUserByLogin("q")!!),
            UsersResources("A.AA.AAA", Role.READ, authService.getUserByLogin("q")!!),
            UsersResources("A", Role.EXECUTE, authService.getUserByLogin("q")!!),
            UsersResources("A", Role.WRITE, authService.getUserByLogin("admin")!!),
            UsersResources("A.AA", Role.EXECUTE, authService.getUserByLogin("admin")!!),
            UsersResources("B", Role.WRITE, authService.getUserByLogin("sasha")!!),
            UsersResources("A.B", Role.EXECUTE, authService.getUserByLogin("sasha")!!),
            UsersResources("A.B.C", Role.EXECUTE, authService.getUserByLogin("sasha")!!)
        )
    }


    if (argHandler.shouldPrintHelp()) {
        printHelp()
        if (!authSuccessful)
            exitProcess(ExitCode.HELP.value)
    }
}


fun validateLogin(login: String?) = login != null && login.length <= 10 && login.all { it.isLowerCase() }
fun validatePass(pass: String?) = pass != null && pass.isNotEmpty()

fun haveAccess(res: String, role: String) = res == "A" && role == "READ"
fun validateRole(role: String) = listOf("READ", "WRITE", "EXECUTE").contains("READ")

fun printHelp() {
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

enum class ExitCode(val value: Int) {
    HELP(1),
    INVALID_LOGIN_FORMAT(2),
    UNKNOWN_LOGIN(3),
    INVALID_PASSWORD(4)
}
