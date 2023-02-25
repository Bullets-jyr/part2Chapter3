package kr.co.bullets.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread {
            try {
                val socket = Socket("192.168.0.103", 8080)
                val printer = PrintWriter(socket.getOutputStream())
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

                printer.println("GET / HTTP/1.1")
                printer.println("Host: 127.0.0.1:8080")
                printer.println("User-Agent: android")
                printer.println("\r\n")
                printer.flush()

                var input: String? = "-1"
                while (input != null) {
                    input = reader.readLine()
                    Log.e("Client", input)
                }
                reader.close()
                printer.close()
                socket.close()
            } catch (e: Exception) {
                Log.e("Client", e.toString())
            }
        }.start()
    }
}