package com.enoch02.datacalc.pages

import androidx.compose.runtime.*
import com.enoch02.datacalc.DataBundleCalculator
import com.enoch02.datacalc.components.layouts.PageLayout
import com.enoch02.datacalc.components.widgets.ResultItem
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

// Container that has a tagline and grid on desktop, and just the tagline on mobile
val HeroContainerStyle = CssStyle {
    base { Modifier.fillMaxWidth().gap(2.cssRem) }
    Breakpoint.MD { Modifier.margin { top(20.vh) } }
}

// A demo grid that appears on the homepage because it looks good
val HomeGridStyle = CssStyle.base {
    Modifier
        .gap(0.5.cssRem)
        .width(70.cssRem)
        .height(18.cssRem)
}

private val GridCellColorVar by StyleVariable<Color>()
val HomeGridCellStyle = CssStyle.base {
    Modifier
        .backgroundColor(GridCellColorVar.value())
        .boxShadow(blurRadius = 0.6.cssRem, color = GridCellColorVar.value())
        .borderRadius(1.cssRem)
}

@Page
@Composable
fun HomePage() {
    var priceInput by remember { mutableStateOf("") }
    val price = priceInput.toDoubleOrNull()

    var dataAmountInput by remember { mutableStateOf("") }
    val dataAmount = dataAmountInput.toDoubleOrNull()

    var validityPeriod by remember { mutableStateOf<Int?>(null) }

    var showResult by remember { mutableStateOf(false) }
    var showWarning by remember { mutableStateOf(false) }

    PageLayout("Calculator") {
        H1 {
            Text("Data Bundle Value Calculator")
        }

        if (showWarning) {
            SpanText(
                "All fields are required!",
                modifier = Modifier.color(Color.rgb(255, 0, 0))
            )
        }

        Form(
            attrs = {
                style {
                    display(DisplayStyle.Flex)
                    flexDirection(FlexDirection.Column)
                    gap(8.px)
                }
                onSubmit { event ->
                    event.preventDefault()
                }
            },
            content = {
                Label { Text("Price (₦):") }
                Input(
                    type = InputType.Text,
                    attrs = {
                        step(0.01)
                        value(priceInput)
                        onInput {
                            priceInput = it.value
                        }
                    }
                )

                Label { Text("Data Amount (GB):") }
                Input(
                    type = InputType.Text,
                    attrs = {
                        step(0.01)
                        value(dataAmountInput)
                        onInput {
                            dataAmountInput = it.value
                        }
                    }
                )

                Label { Text("Validity Period (Days):") }
                NumberInput {
                    value(validityPeriod ?: 0)
                    onInput { validityPeriod = it.value?.toInt() }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Button(
                            attrs = {
                                type(ButtonType.Submit)
                                onClick {
                                    if (price != null && validityPeriod != null && dataAmount != null) {
                                        showWarning = false
                                        showResult = true
                                    } else {
                                        showWarning = true
                                    }
                                }
                                style {
                                    marginRight(16.px)
                                }
                            },
                            content = { Text("Calculate") }
                        )

                        Button(
                            attrs = {
                                type(ButtonType.Reset)
                                onClick {
                                    priceInput = ""
                                    dataAmountInput = ""
                                    validityPeriod = null

                                    showResult = false
                                }
                            },
                            content = { Text("Clear") }
                        )
                    }
                )
            }
        )

        //TODO: add the ability to select currencies
        if (showResult) {
            val results = DataBundleCalculator.calculateAllMetrics(price!!, dataAmount!!, validityPeriod!!)

            Column(modifier = Modifier.fillMaxWidth()) {
                H1 {
                    Text("Result")
                }

                ResultItem("Value Per GB: ", value = "₦${results["valuePerGB"]}")
                ResultItem("Value Per Day: ", value = "₦${results["valuePerDay"]}")
                ResultItem("Value Per GB Per Day: ", value = "₦${results["valuePerGBPerDay"]}")
                ResultItem("Days Per GB: ", value = "${results["daysPerGB"]} days")
                ResultItem("GB Per Day: ", value = "${results["gbPerDay"]} GB")
            }
        }
    }
}
