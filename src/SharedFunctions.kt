import java.io.File

fun main(args: Array<String>) {
    val main = Main()
    main.call(args)

}

class Main {
    companion object {
        val actionFromTxt = ActionFromTxt()
    }

    fun call(args: Array<String>) {
        when (args[0]) {
            "action" -> actionFromTxt.call()
        }
        for (action in ActionFromTxt.actions) {
            action.call()
        }
        for (cardContainer in ActionFromTxt.cardContainers) {
            println(cardContainer.toString())
        }

    }
}

/**
 * Reads file from resources folder based on file name, returns list of lines
 */
fun readFileAsLines(fileName: String): List<String> = File(fileName).useLines { it.toList() }
