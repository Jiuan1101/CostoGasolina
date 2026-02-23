package com.mexiti.costogasolina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mexiti.costogasolina.ui.theme.CostoGasolinaTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CostoGasolinaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CostGasLayout("Android")
                }
            }
        }
    }
}

@Composable
fun CostGasLayout(name: String, modifier: Modifier = Modifier) {
    var precioLitroEntrada by remember{
        mutableStateOf("")
    }

    var cantLitrosEntrada by remember {
        mutableStateOf("")
    }

    var propinaEntrada by remember {
        mutableStateOf("")
    }

    var agregarPropina by remember { mutableStateOf(false)
    }

    val precioLitro = precioLitroEntrada.toDoubleOrNull()  ?: 0.0
    val cantLitros = cantLitrosEntrada.toDoubleOrNull()  ?: 0.0
    val propina = propinaEntrada.toDoubleOrNull()  ?: 0.0

    val subtotal = precioLitro * cantLitros
    val totalConPropina = if (agregarPropina) subtotal + propina else subtotal
    val total = NumberFormat.getCurrencyInstance().format(totalConPropina)
    

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.beige),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.4F
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(all = 50.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.calcular_monto),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            EditNumberField(
                label = R.string.ingresa_gasolina,
                leadingIcon = R.drawable.gas_24,
                keyboardsOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                value = precioLitroEntrada,
                onValueChanged = {
                    precioLitroEntrada = it
                },
                modifier = Modifier.fillMaxWidth()
            )
            EditNumberField(
                label = R.string.litros,
                leadingIcon = R.drawable.gasolina,
                keyboardsOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                value = cantLitrosEntrada,
                onValueChanged = {
                    cantLitrosEntrada = it
                },
                modifier = Modifier.fillMaxWidth()
            )
            //Componente personalizado
            EditNumberField(
                label = R.string.propina,
                leadingIcon = R.drawable.propina_24,
                keyboardsOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                value = propinaEntrada,
                onValueChanged = {
                    propinaEntrada = it
                },
                modifier = Modifier.fillMaxWidth()
            )

            Switch(
                checked = agregarPropina,
                onCheckedChange =  {
                    estado -> agregarPropina = estado
                }
            )
            Text(
                text = stringResource(R.string.monto_total, total),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardsOptions:KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        label = { Text(text = stringResource(id = label))  },
        value = value,
        singleLine = true,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon) , contentDescription = null) },
        keyboardOptions = keyboardsOptions,
        modifier = modifier,
        onValueChange = onValueChanged
    )

}

@Preview(showBackground = true)
@Composable
fun CostGasLayoutPreview() {
    CostoGasolinaTheme {
        CostGasLayout("Android")
    }
}

private fun calcularMonto(precio:Double, cantLitros: Double):String{
    val monto = precio * cantLitros
    return NumberFormat.getCurrencyInstance().format(monto)
}
