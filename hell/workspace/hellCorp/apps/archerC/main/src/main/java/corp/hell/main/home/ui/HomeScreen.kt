package corp.hell.main.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import corp.hell.kernel.theme.AppTheme
import corp.hell.main.home.uiComponent.GreetingText

@Composable
fun HomeScreen() {
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .height(IntrinsicSize.Max)
                    .padding(15.dp, 35.dp, 0.dp, 0.dp)
            ) {
                GreetingText(name = "Harsh")
                GreetingText(name = "Happie")
                GreetingText(name = "Karuna")
                GreetingText(name = "Satish")
                GreetingText(name = "Nando")
                GreetingText(name = "Shiva")
                GreetingText(name = "Khushbu")
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}