package com.example.furusatoup.calculationtraining

import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.concurrent.schedule

class TestActivity : AppCompatActivity(), View.OnClickListener {
    var numberOfQuestion : Int = 0

    var numberOfRemaining: Int = 0

    var numberOfCorrect : Int = 0

    lateinit var soundPool: SoundPool

    var intSoundIdCorrect : Int = 0

    var intSoundIdIncorrect : Int = 0

    lateinit var timer : Timer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val bundle = intent.extras
        numberOfQuestion = bundle.getInt("numberOfQuestion")
        textViewRemaining.text = numberOfQuestion.toString()
        numberOfRemaining = numberOfQuestion
        numberOfCorrect = 0

//        答えあわせボタンが押されたら
        buttonAnswerCheck.setOnClickListener {
            if (textViewAnswer.text.toString() != "" && textViewAnswer.text.toString() != "-") {
                answerCheck()
            }
        }

//        戻るボタンが押されたら
        buttonBack.setOnClickListener {
//            前の画面に戻るだけならfinish
            finish()
        }

        button0.setOnClickListener(this)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
        button8.setOnClickListener(this)
        button9.setOnClickListener(this)
        buttonMinus.setOnClickListener(this)
        buttonC.setOnClickListener(this)

        question()
    }

    override fun onResume() {
        super.onResume()
//        soundPoolの準備
        soundPool =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            SoundPool.Builder().setAudioAttributes(AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build())
                    .setMaxStreams(1)
                    .build()
        } else {
            SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        }
//        効果音ファイルをメモリにロード
        intSoundIdCorrect = soundPool.load(this,R.raw.sound_correct,1)
        intSoundIdIncorrect = soundPool.load(this,R.raw.sound_incorrect,1)

//        タイマーの準備
        timer = Timer()

    }

    override fun onPause() {
        super.onPause()
//        効果音の後片付け
        soundPool.release()
//        タイマーのキャンセル
        timer.cancel()
    }

    private fun question() {
        buttonBack.isEnabled = false
        buttonAnswerCheck.isEnabled = true
        button0.isEnabled = true
        button1.isEnabled = true
        button2.isEnabled = true
        button3.isEnabled = true
        button4.isEnabled = true
        button5.isEnabled = true
        button6.isEnabled = true
        button7.isEnabled = true
        button8.isEnabled = true
        button9.isEnabled = true
        buttonMinus.isEnabled = true
        buttonC.isEnabled = true

        val random = Random()
        val intQuestionLeft = random.nextInt(100) + 1
        val intQuestionRight = random.nextInt(100) + 1
        textViewLeft.text = intQuestionLeft.toString()
        textViewRight.text = intQuestionRight.toString()

        when(random.nextInt(2) + 1){
            1 -> textViewOperator.text = "+"
            2 -> textViewOperator.text = "-"
        }

        textViewAnswer.text = ""

        imageView.visibility = View.VISIBLE

    }

    private fun answerCheck() {
        buttonBack.isEnabled = false
        buttonAnswerCheck.isEnabled = false
        button0.isEnabled = false
        button1.isEnabled = false
        button2.isEnabled = false
        button3.isEnabled = false
        button4.isEnabled = false
        button5.isEnabled = false
        button6.isEnabled = false
        button7.isEnabled = false
        button8.isEnabled = false
        button9.isEnabled = false
        buttonMinus.isEnabled = false
        buttonC.isEnabled = false

        numberOfRemaining -= 1
        textViewRemaining.text = numberOfRemaining.toString()

//        画像を見えるようにする
        imageView.visibility = View.VISIBLE

        val intMyAnswer : Int = (textViewAnswer.text.toString() ).toInt()

        val intRealAnswer : Int =
                if (textViewOperator.text == "+"){
                    textViewLeft.text.toString().toInt() + textViewRight.text.toString().toInt()
                } else {
                    textViewLeft.text.toString().toInt() - textViewRight.text.toString().toInt()
                }
        if (intMyAnswer == intRealAnswer){
            numberOfCorrect += 1
            textViewCorrect.text = numberOfCorrect.toString()
            imageView.setImageResource(R.drawable.pic_correct)
            soundPool.play(intSoundIdCorrect, 1.0f, 1.0f, 0, 0, 1.0f)
        } else {
            imageView.setImageResource(R.drawable.pic_incorrect)
            soundPool.play(intSoundIdIncorrect, 1.0f, 1.0f, 0, 0, 1.0f)
        }

        val intPoint : Int = ( ( numberOfCorrect.toDouble() / (numberOfQuestion.toDouble() - numberOfRemaining.toDouble()) ) * 100 ).toInt()
        textViewPoint.text = intPoint.toString()

        if (numberOfRemaining == 0){
//            残り問題数がなくなった場合の処理
            buttonBack.isEnabled = true
            buttonAnswerCheck.isEnabled = false
            textViewMessage.text = "テスト終了"
        } else {
//            問題が残っている場合の処理
            timer.schedule(1000, { runOnUiThread { question() } })

        }

    }

    override fun onClick(v: View?) {
        val button : Button = v as Button
        when(v?.id){
            R.id.buttonC -> textViewAnswer.text = ""

            R.id.buttonMinus -> if(textViewAnswer.text.toString() == "")
                                textViewAnswer.text = "-"

            R.id.button0 -> if (textViewAnswer.text.toString() != "0" && textViewAnswer.text != "-" )
                            textViewAnswer.append(button.text)


            else -> if(textViewAnswer.text.toString() == "0")
                textViewAnswer.text = button.text
                else textViewAnswer.append(button.text)
        }
    }

}

