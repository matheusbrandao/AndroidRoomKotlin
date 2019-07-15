package com.example.androidroomkotlin.ui.activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.example.androidroomkotlin.R
import com.example.androidroomkotlin.database.Database
import com.example.androidroomkotlin.database.dao.AlunoDAO
import com.example.androidroomkotlin.model.Aluno
import com.example.androidroomkotlin.ui.activity.ConstantesActivities.CHAVE_ALUNO
import kotlinx.android.synthetic.main.activity_formulario_aluno.*

class FormularioAlunoActivity : AppCompatActivity() {

    private lateinit var alunoDao : AlunoDAO
    private lateinit var campoNome: EditText
    private lateinit var campoTelefone: EditText
    private lateinit var campoEmail: EditText
    private var aluno: Aluno? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_aluno)

        val database = Database.instance(this)
        alunoDao = database.alunoDao()

        inicializacaoDosCampos()
        carregaAluno()
    }

    private fun inicializacaoDosCampos() {
        campoNome = activity_formulario_aluno_nome
        campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone)
        campoEmail = findViewById(R.id.activity_formulario_aluno_email)
    }

    private fun carregaAluno() {
        intent.getParcelableExtra<Aluno>(CHAVE_ALUNO)?.let {
            aluno = it
            preencheCampos()
        }
    }

    private fun preencheCampos() {
        campoNome.setText(aluno!!.nome)
        campoTelefone.setText(aluno!!.telefone)
        campoEmail.setText(aluno!!.email)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_formulario_aluno_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var itemId = item!!.itemId

        if (itemId == R.id.activity_formulario_aluno_menu_salvar)
            saveProduct()

        return super.onOptionsItemSelected(item)
    }

    private fun saveProduct() {
        SaveNote().execute()
    }

    inner class SaveNote : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            val createdProduct = create()

            if (createdProduct.id == 0L)
                alunoDao.add(createdProduct)
            else
                alunoDao.update(createdProduct)

            finish()
            return null
        }
    }

    private fun create(): Aluno {
        var nome = campoNome.text.toString()
        var email = activity_formulario_aluno_email.text.toString()
        var telefone = activity_formulario_aluno_telefone.text.toString()

        if (aluno == null)
            return Aluno(nome = nome, email = email, telefone = telefone)
        else
            return Aluno(id = aluno!!.id, nome = nome, email = email, telefone = telefone)
    }
}
