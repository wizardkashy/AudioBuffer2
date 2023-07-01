package com.example.audiobuffer2

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.audiobuffer2.ui.theme.AudioBuffer2Theme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioBuffer2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Settings()
                    }
                }
            }
        }
    }
}

@Composable
fun Settings()
{
    var duration by remember { mutableStateOf("")}
    var storageuri by remember { mutableStateOf<Uri?>(null) }
    DurationInput()
    {
        duration = it
    }
    FilePicker()
    {
        storageuri = it
    }

    Text("duration = " + duration)
    Text("uri = " + storageuri)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DurationInput(onDurationChange: (String) -> Unit)
{
    // Duration Input
    var duration by remember { mutableStateOf("") }
    TextField(
        value = duration,
        onValueChange =
        {
            duration = it.filter { it.isDigit() }
            if (duration.toInt() > 30)
            {
                duration = "30"
            }
            onDurationChange(duration)
        },
        label = { Text(text="Duration (s)")},
        placeholder = { Text(text="ex. 30")},
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        maxLines = 1
    )
}

@Composable
fun FilePicker(onActivityResult: (Uri) -> Unit)
{
    val launcher = rememberLauncherForActivityResult(contract =
        ActivityResultContracts.OpenDocumentTree())
    { uri: Uri? ->
        uri?.let {onActivityResult(it)}
    }
    Button(
        onClick = {launcher.launch(null)},
        modifier = Modifier.padding(16.dp)
    )
    {
        Text("Pick Storage Folder")
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AudioBuffer2Theme {
        Greeting("Android")
    }
}