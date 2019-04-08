import java.io.File
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor

fun classFromTxt() {
    //Read all lines into array
    val input =
        readFileAsLines("C:\\Users\\392037\\Desktop\\Prototypes\\out\\production\\Prototypes\\Resources\\CreateClassFromTxt.txt")
    //Create an array containing all lines split into arrays by ' '.
    val inputPerLine = input.map { it.split(" ") }

    //Iterate over input
    for (i in inputPerLine.indices) {
        //Create map of KParameters
        val parameterArrayByKParameter: MutableMap<KParameter, Any> = mutableMapOf()
        //First word within line is the name of the class, use custom defined map to find .class by name, get list of param names from it
        val parameterNames = classNameMap[inputPerLine[i][0]]?.primaryConstructor?.parameters
        //Iterate over the parameters within an input line
        for (j in inputPerLine[i].indices) {
            //Skip class name
            if (j == 0) continue
            //Split parameter into parts by '=' and '.'. Eg: String.name=Ace becomes [0]="String" [1]="name" [2]="Ace"
            val splitLine = inputPerLine[i][j].split("=", ".")
            //paramIndex stores position of parameter in class constructor
            var paramIndex: Int? = null
            //Find position of parameter in class constructor
            if (parameterNames != null) {
                for (k in parameterNames.indices) {
                    if (parameterNames[k].name==splitLine[1]) paramIndex=k
                }
            }
            //If parameter has no position in class constructor skip it
            if (paramIndex==null) continue
            //Convert value to correct type using custom map and save it by parameter name
            parameterArrayByKParameter[parameterNames!![paramIndex]] = convertToBaseType(splitLine[0], splitLine[2])
        }
        //Create class based on found class name and parameters
        val createdClass = classNameMap[inputPerLine[i][0]]?.primaryConstructor?.callBy(parameterArrayByKParameter)

        println(createdClass.toString())
    }
}