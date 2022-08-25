package machine


class CoffeeMachine {


    var WATER = 400
    var MILK  = 540
    var BEANS = 120
    var CUPS = 9
    var MONEY = 550

    var coffeeState = CoffeeStates.START.state
    var fillStates = FillStates.WATER.state

    var bool = true

    fun processInput(input: String){

        if (coffeeState == CoffeeStates.START.state) {
            when (input) {
                "buy" -> buy()
                "fill" -> fill()
                "take" -> take()
                "remaining" -> outputRemaining()
                "exit" -> bool = false
                else -> println("Incorrect Input")
            }
        } else if(coffeeState == CoffeeStates.BUY.state){
            when (input) {
                "1" -> makeEspresso()
                "2" -> makeLatte()
                "3" -> makeCappuccino()
                "back" ->  coffeeState = CoffeeStates.START.state
            }
        } else if(coffeeState == CoffeeStates.FILL.state){
            when(fillStates){
                FillStates.WATER.state -> {
                    WATER += input.toInt()
                    fillStates = FillStates.MILK.state
                    fill()
                }
                FillStates.MILK.state ->  {
                    MILK += input.toInt()
                    fillStates = FillStates.BEANS.state
                    fill()
                }
                FillStates.BEANS.state -> {
                    BEANS += input.toInt()
                    fillStates = FillStates.CUPS.state
                    fill()
                }
                FillStates.CUPS.state ->{
                    CUPS += input.toInt()
                    fillStates = FillStates.END.state
                    coffeeState = CoffeeStates.START.state
                }
            }
        }
    }

    private  fun fill() {
       coffeeState = CoffeeStates.FILL.state

       when(fillStates) {
           FillStates.WATER.state -> println("Write how many ml of water do you want to add: ")

           FillStates.MILK.state -> println ("Write how many ml of milk do you want to add:")

           FillStates.BEANS.state -> println("Write how many grams of coffee beans do you want to add:")

           FillStates.CUPS.state -> println("Write how many disposable cups of coffee do you want to add:")

       }
    }

    enum class FillStates(val state: String){
        WATER("water"),
        MILK("milk"),
        BEANS("beans"),
        CUPS("cups"),
        END(" ")
    }

  private  fun makeCappuccino() {

        if (WATER >= 200 && MILK >= 100 && BEANS >= 12 && CUPS >=1) {
            println("I have enough resources, making you a coffee!")

            WATER -= 200
            MILK -= 100
            BEANS -= 12
            CUPS -= 1
            MONEY += 6
        } else if( WATER < 200) {
            println("Sorry, not enough water!")
        } else if (MILK < 100) {
            println("Sorry, not enough milk!")
        } else if (BEANS < 12) {
            println("Sorry, not enough beans!")
        } else {
            println("Sorry, not enough cups!")
        }
      coffeeState = CoffeeStates.START.state

    }

    private fun makeLatte() {

        if (WATER >= 350 && MILK >= 75 && BEANS >= 20 && CUPS >=1) {

            println("I have enough resources, making you a coffee!")
            WATER -= 350
            MILK -= 75
            BEANS -= 20
            CUPS -= 1
            MONEY += 7
        } else if( WATER < 350) {
            println("Sorry, not enough water!")
        } else if (MILK < 75) {
            println("Sorry, not enough milk!")
        } else if (BEANS < 20) {
            println("Sorry, not enough beans!")
        } else {
            println("Sorry, not enough cups!")
        }
        coffeeState = CoffeeStates.START.state
    }


   private fun makeEspresso() {

        if (WATER >= 250 && BEANS >= 16 && CUPS >=1) {
            println("I have enough resources, making you a coffee!")

            WATER -= 250
            BEANS -= 16
            CUPS -= 1
            MONEY += 4
        } else if(WATER < 250) {
            println("Sorry, not enough water!")
        } else if (BEANS < 16) {
            println("Sorry, not enough beans!")
        } else {
            println("Sorry, not enough cups!")
        }
       coffeeState = CoffeeStates.START.state
    }


    fun startMachine() {
            println("Write action (buy, fill, take, remaining, exit):")

    }

    private fun buy() {
        coffeeState = CoffeeStates.BUY.state
        println("""
        What do you want to buy? 1 - espresso 2 - latte, 3 - cappuccino, back - to main menu:
    """.trimIndent())
    }

    private fun take() {
        println("I gave you $$MONEY")
        MONEY = 0
    }

    private  fun outputRemaining() {
        println("""
        The coffee machine has:
        $WATER ml of water
        $MILK ml of milk
        $BEANS g of coffee beans
        $CUPS disposable cups
        $$MONEY of money
    """.trimIndent())

    }
}

enum class CoffeeStates (val state: String) {
    START("start"),
    BUY("buy"),
    FILL("fill"),
    REMAINING("remaining"),
    TAKE("take")

}
fun main() {

    val coffeeMachine = CoffeeMachine()
    while(coffeeMachine.bool) {
        coffeeMachine.startMachine()
        val input = readln()
        coffeeMachine.processInput(input)
            when (coffeeMachine.coffeeState) {
                //CoffeeStates.START.state -> coffeeMachine.processInput(readln())
                CoffeeStates.BUY.state -> coffeeMachine.processInput(readln())
                CoffeeStates.FILL.state -> {
                    do {
                        when (coffeeMachine.fillStates) {

                            CoffeeMachine.FillStates.WATER.state -> coffeeMachine.processInput(
                                readln()
                            )

                            CoffeeMachine.FillStates.MILK.state -> coffeeMachine.processInput(readln())

                            CoffeeMachine.FillStates.BEANS.state -> coffeeMachine.processInput(
                                readln()
                            )

                            CoffeeMachine.FillStates.CUPS.state -> coffeeMachine.processInput(readln())
                            else -> break
                        }
                    } while(coffeeMachine.fillStates.isNotEmpty())
                }
            }
    }
}
