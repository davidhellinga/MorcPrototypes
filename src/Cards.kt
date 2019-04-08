
interface ICard {
    val name: String
    val container: ICardContainer
}

class Card(override val name: String, private val value: Int, override val container: ICardContainer) : ICard {
    override fun toString(): String {
        return "$name is worth $value points and is currently in ${container.name}"
    }
}

class MagicCard(
    override val name: String,
    private val manacost: Int,
    private val power: Int,
    private val toughness: Int, override val container: ICardContainer
) : ICard {
    override fun toString(): String {
        return "$name costs $manacost to cast, its power/toughness is $power/$toughness and is currently in ${container.name}"
    }
}