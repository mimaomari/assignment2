package za.ac.iie.a2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var welcomeText: TextView
    private lateinit var startButton: Button
    private lateinit var questionText: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: TextView
    private lateinit var feedbackText: TextView
    private lateinit var scoreText: TextView
    private lateinit var reviewButton: Button
    private lateinit var exitButton: Button
    private lateinit var container: ConstraintLayout

    private val questions = arrayOf(
        "The sky is blue",
        "2 + 2 = 5",
        "Water is wet",
        "The sun is cold",
        "Grass is pink"
    )
    private val answers = arrayOf(true, false, true, false, false)
    private var score = 0
    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        welcomeText = findViewById(R.id.welcomeText)
        startButton = findViewById(R.id.startButton)
        questionText = findViewById(R.id.questionText)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        nextButton = findViewById(R.id.nextButton)
        feedbackText = findViewById(R.id.feedbackText)
        scoreText = findViewById(R.id.scoreText)
        reviewButton = findViewById(R.id.reviewButton)
        exitButton = findViewById(R.id.exitButton)
        container = findViewById(R.id.container)

        // Welcome Screen Logic
        welcomeText.text = "Welcome to the Flashcard App!\nStudy with flashcards, answer questions, and get your score."
        startButton.setOnClickListener {
            showFlashcardQuestionScreen()
        }

        // Initially hide other screens
        questionText.visibility = android.view.View.GONE
        trueButton.visibility = android.view.View.GONE
        falseButton.visibility = android.view.View.GONE
        nextButton.visibility = android.view.View.GONE
        feedbackText.visibility = android.view.View.GONE
        scoreText.visibility = android.view.View.GONE
        reviewButton.visibility = android.view.View.GONE
        exitButton.visibility = android.view.View.GONE
    }

    private fun showFlashcardQuestionScreen() {
        welcomeText.visibility = android.view.View.GONE
        startButton.visibility = android.view.View.GONE
        questionText.visibility = android.view.View.VISIBLE
        trueButton.visibility = android.view.View.VISIBLE
        falseButton.visibility = android.view.View.VISIBLE
        nextButton.visibility = android.view.View.VISIBLE
        feedbackText.visibility = android.view.View.VISIBLE

        loadQuestion()

        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }
        nextButton.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                loadQuestion()
                feedbackText.text = ""
            } else {
                showScoreScreen()
            }
        }
    }

    private fun loadQuestion() {
        questionText.text = questions[currentQuestionIndex]
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = answers[currentQuestionIndex]
        feedbackText.text = if (userAnswer == correctAnswer) "Correct!" else "Incorrect!"
        if (userAnswer == correctAnswer) score++
    }

    private fun showScoreScreen() {
        questionText.visibility = android.view.View.GONE
        trueButton.visibility = android.view.View.GONE
        falseButton.visibility = android.view.View.GONE
        nextButton.visibility = android.view.View.GONE
        feedbackText.visibility = android.view.View.GONE
        scoreText.visibility = android.view.View.VISIBLE
        reviewButton.visibility = android.view.View.VISIBLE
        exitButton.visibility = android.view.View.VISIBLE

        scoreText.text = "Your score: $score out of 5\n${getFeedback()}"
        reviewButton.setOnClickListener {
            // Logic to review questions and answers
            scoreText.text = questions.joinToString("\n") { q -> "$q - ${if (answers[questions.indexOf(q)] == true) "True" else "False"}" }
        }
        exitButton.setOnClickListener {
            finish()
        }
    }

    private fun getFeedback(): String {
        return when (score) {
            4, 5 -> "Great job!"
            2, 3 -> "Good effort!"
            else -> "Keep practicing!"
        }
    }
}