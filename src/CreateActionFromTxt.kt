import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor

class ActionFromTxt {
    companion object {
        var cards: MutableList<ICard> = mutableListOf()
        var cardContainers: MutableList<ICardContainer> = mutableListOf()
        var actions: MutableList<IAction> = mutableListOf()
    }

    fun call() {
        //Read all lines into array
        val input =
            readFileAsLines("C:\\Users\\392037\\Desktop\\Prototypes\\out\\production\\Prototypes\\Resources\\CreateActionFromTxt.txt")
        //Create an array containing all lines split into arrays by ' '.
        val inputPerLine = input.map { it.split(" ") }


        //Iterate over input
        for (i in inputPerLine.indices) {
            if (inputPerLine[i][0]=="//") continue
            //Create map of KParameters
            val parameterArrayByKParameter: MutableMap<KParameter, Any> = mutableMapOf()
            //First word within line is the name of the class, use custom defined map to find .class by name, get list of param names from it
            val parameterNames = classNameMap[inputPerLine[i][0]]?.primaryConstructor?.parameters
            //Iterate over the parameters within an input line
            for (j in inputPerLine[i].indices) {
                //Skip class name
                if (j == 0) continue
                //Split parameter into parts by '=' and '.'. Eg: String.name=Ace becomes [0]="String" [1]="name" [2]="Ace"
                val splitParam = inputPerLine[i][j].split("=", ".")
                //paramIndex stores position of parameter in class constructor
                var paramIndex: Int? = null
                //Find position of parameter in class constructor
                if (parameterNames != null) {
                    for (k in parameterNames.indices) {
                        if (parameterNames[k].name == splitParam[1]) paramIndex = k
                    }
                }
                //If parameter has no position in class constructor skip it
                if (paramIndex == null) continue
                //Convert value to correct type using custom map and save it by parameter name
                parameterArrayByKParameter[parameterNames!![paramIndex]] =
                    convertToBaseType(splitParam[0], splitParam[2])
            }
            //Create class based on found class name and parameters
            val createdClass = classNameMap[inputPerLine[i][0]]?.primaryConstructor?.callBy(parameterArrayByKParameter)
            //Maintain lists of previously created classes
            when (createdClass) {
                is ICardContainer -> cardContainers.add(createdClass)
                is IAction -> actions.add(createdClass)
                is ICard -> {
                    cards.add(createdClass)
                    createdClass.container.cards.add(createdClass)
                    println(createdClass.toString())
                }
            }
        }
    }
}