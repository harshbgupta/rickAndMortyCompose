package corp.hell.main.home.uiComponent

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import corp.hell.kernel.theme.defaultTextStyle

@Composable
fun GreetingText(name: String) {
    Text("Hello, $name!", style = defaultTextStyle)
}
