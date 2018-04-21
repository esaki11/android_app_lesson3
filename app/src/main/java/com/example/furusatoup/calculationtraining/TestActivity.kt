package com.example.furusatoup.calculationtraining

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*

class TestActivity : AppCompatActivity(), View.OnClickListener {
    var numberOfRemaining: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val bundle = intent.extras
        val numberOfQuestion : Int = bundle.getInt("numberOfQuestion")
        textViewRemaining.text = numberOfQuestion.toString()
        numberOfRemaining = numberOfQuestion

        buttonAnswerCheck.setOnClickListener {
            answerCheck()
        }

        buttonBack.setOnClickListener {  }

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

