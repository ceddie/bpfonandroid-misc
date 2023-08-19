package com.example.exampleapp

import android.os.Bundle
import android.os.Process
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.exampleapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private fun readFile(): String {
        val context = applicationContext
        var content = "NA"
        try {
            val fileName = "INFO_258547688934685594.tmp"
            val cacheFile = File(context.cacheDir, fileName)
            val isNewFileCreated: Boolean = cacheFile.createNewFile()
            if (isNewFileCreated) {
                cacheFile.writeText("Hello World!")
                Log.i("INFO", "${context.cacheDir}/$fileName is created successfully.")
            } else {
                Log.i("INFO", "${context.cacheDir}/$fileName already exists.")
            }
            content = cacheFile.readText(Charsets.UTF_8)
            Log.i("INFO", content)
        } catch (ex: IOException) {
            // Error occurred while creating the File
            Log.i("Error", ex.toString())
        }
        val msg = "Read file: \"$content\""
        Log.i("INFO", msg)

        return msg
    }

    private fun executeCommand(): String {
        val context = applicationContext
        val cmd = "ls ${context.cacheDir}"
        val proc = Runtime.getRuntime().exec(cmd)
        val stdInput = BufferedReader(InputStreamReader(proc.inputStream))
        val output = stdInput.use(BufferedReader::readText).trim()
        val msg = "Executed command: ${cmd.substring(0, 10)}... -> $output"
        val fullMsg = "Executed command: $cmd -> $output"
        Log.i("INFO", fullMsg)

        return msg
    }

    private fun openSocketAndSendBytes(): String {
        val host = "datenburg.org"
        val port = 80
        val thread = Thread {
            Log.i("INFO", "SOCKET TID: " + Process.myTid().toString())
            try {
                val socket = Socket(host, port)
                val outputStream = DataOutputStream(socket.getOutputStream())
                val data = "Hello!".toByteArray()
                outputStream.write(data)
                outputStream.flush()
                outputStream.close()
                socket.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
        Log.i("INFO", "UI TID: " + Process.myTid().toString())
        val msg = "Opened socket: $host:${port}"
        Log.i("INFO", msg)

        return msg
    }

    private fun decodeString(): String {
        val res = String(Base64.decode("SGVsbG8gV29ybGQh", Base64.DEFAULT))
        val msg = "Decoded string: \"$res\""
        Log.i("INFO", msg)
        return msg
    }

    private fun writeAndDeleteFile(): String {
        val fileName = "myFile.txt"
        val fileContent = "This will be deleted!"

        val context = applicationContext
        val file = File(context.cacheDir, fileName)
        file.writeText(fileContent)
        Log.i("INFO","File created: $fileName")

        if (file.exists()) {
            //val path = file.path
            //Log.i("INFO", "file.path = $path")
            file.delete()
            Log.i("INFO","File deleted: $fileName")
        } else {
            Log.i("INFO","File doesn't exist: $fileName")
        }
        val msg = "File created and deleted."
        Log.i("INFO", msg)
        return msg
    }

    private fun button1(view: View){
        val msg = readFile()
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun button2(view: View) {
        val msg = executeCommand()
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun button3(view: View) {
        val msg = openSocketAndSendBytes()
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun button4(view: View) {
        val msg = decodeString()
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun button5(view: View) {
        val msg = writeAndDeleteFile()
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun button6(view: View) {
        val res = checkSelfPermission("android.permission.CAMERA")
        val msg = "called and checkSelfPermission() with result $res"
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun button7(view: View) {
        val msg = "not action implemented yet"
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun button8(view: View) {
        val msg = "not action implemented yet"
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

    }

    private fun button9(view: View) {
        val msg = "not action implemented yet"
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab1.setOnClickListener { view ->
            button1(view)
        }
        binding.fab2.setOnClickListener { view ->
            button2(view)
        }
        binding.fab3.setOnClickListener { view ->
            button3(view)
        }
        binding.fab4.setOnClickListener { view ->
            button4(view)
        }
        binding.fab5.setOnClickListener { view ->
            button5(view)
        }
        binding.fab6.setOnClickListener { view ->
            button6(view)
        }
        binding.fab7.setOnClickListener { view ->
            button7(view)
        }
        binding.fab8.setOnClickListener { view ->
            button8(view)
        }
        binding.fab9.setOnClickListener { view ->
            button9(view)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}