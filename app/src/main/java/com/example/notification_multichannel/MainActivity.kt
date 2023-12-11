package com.example.notification_multichannel

import android.Manifest
import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    var NOTIFICATION_ID1: Int = 1
    var NOTIFICATION_ID2: Int = 2

    var TITLE_PUSH_NOTIFICATION : String = "Mỹ đã phủ quyết một nghị quyết"
    var CONTENT_PUSH_NOTIFICATION : String = "Theo Reuters và BBC, trong cuộc bỏ phiếu diễn ra vào ngày 8/12, 13 thành viên của Hội đồng Bảo an đã ủng hộ dự thảo nghị quyết ngắn gọn do Các tiểu vương quốc Ảrập thống nhất (UAE) đưa ra, Anh bỏ phiếu trắng và Mỹ phản đối. \n" +
            "\n" +
            "Cuộc bỏ phiếu diễn ra sau khi Tổng thư ký Liên Hợp Quốc (LHQ) Antonio Guterres có động thái hiếm hôm 6/12 để chính thức cảnh báo hội đồng gồm 15 thành viên về mối đe dọa toàn cầu xuất phát từ cuộc xung đột kéo dài 2 tháng giữa Israel và Hamas. "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //button1
        val btnSendNotification1: Button = findViewById(R.id.btn_send_notification1)
        btnSendNotification1.setOnClickListener{
            sendNotificationChannel1()
        }

        //button2
        val btnSendNotification2: Button = findViewById(R.id.btn_send_notification2)
        btnSendNotification2.setOnClickListener{
            sendNotificationChannel2()
        }
    }
    private fun sendNotificationChannel1(){
        var uri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var bitmap : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.vid1)

        val notification : Notification = NotificationCompat.Builder(this, MyApplication.getInstance().CHANNEL_ID1)
            .setContentTitle(TITLE_PUSH_NOTIFICATION)
            .setContentText(CONTENT_PUSH_NOTIFICATION)
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setStyle(NotificationCompat.BigTextStyle().bigText(CONTENT_PUSH_NOTIFICATION)) //  hiển thị toàn bộ nội dung của đoạn contentText dài.
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))// hiện picture lên notification
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setLargeIcon(BitmapFactory.decodeResource(this.resources,
                R.drawable.ic_notification_custom
            )) // có thể truyền bitmap hoặc Icon
            .setSound(uri) // lấy âm thanh mặc định của push notification
            .build()

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
        notificationManagerCompat.notify(NOTIFICATION_ID1, notification)
    }

    private fun sendNotificationChannel2(){
        val customSoundUri = Uri.parse("android.resource://"+ packageName +"/"+{R.raw.sound_custom})
        val notification : Notification = NotificationCompat.Builder(this, MyApplication.getInstance().CHANNEL_ID2)
            .setContentTitle("notification 2")
            .setContentText("This is message of push notification")
            .setSmallIcon(R.drawable.ic_notification_custom)
//            .setPriority(NotificationCompat.PRIORITY_HIGH) // set độ ưu tiên cho thông báo, từ android 8.0, có thể set độ ưu tiên qua channel ( sang file MyAppllication và search "importance1")
            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_notification_custom)) // có thể truyền bitmap hoặc Icon
            .setSound(customSoundUri)
            .build()

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }
        notificationManagerCompat.notify(NOTIFICATION_ID2, notification)
    }
}

