package com.user.contacts

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.user.contacts.viewmodels.ContactsViewModel

sealed class DestinationScreen(var route: String){
    object MainScreen : DestinationScreen("mainscreen")
}

class MainActivity : ComponentActivity() {
    private val viewModel: ContactsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.fetchContacts()
        super.onCreate(savedInstanceState)
        setContent {
            RequestPermissions(this)
            AddContactsScreen(viewModel = viewModel, contentResolver = contentResolver)

        }
    }
    @Composable
    fun AddContactsScreen(viewModel: ContactsViewModel, contentResolver: ContentResolver) {
        val contacts = viewModel.contacts.observeAsState(emptyList())
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(text = "Contact Sync App", fontSize = 30.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { viewModel.addContactsIfToday(contentResolver)
                          Toast.makeText(context, "Numbers are Added",Toast.LENGTH_SHORT).show()},
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Sync Contacts")
            }
            
//            Button(onClick = { /*TODO*/ }) {
//                Text(text = "Delete Contacts")
//            }
        }
    }
    @Composable
    fun RequestPermissions(activity: ComponentActivity) {
        val requestPermissionsLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

        }

        LaunchedEffect(Unit) {
            val permissionsToRequest = mutableListOf<String>()

            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.READ_CONTACTS)
            }

            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.WRITE_CONTACTS)
            }

            if (permissionsToRequest.isNotEmpty()) {
                requestPermissionsLauncher.launch(permissionsToRequest.toTypedArray())
            }
        }
    }
}





