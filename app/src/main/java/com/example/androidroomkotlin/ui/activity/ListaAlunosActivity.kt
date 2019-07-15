package com.example.androidroomkotlin.ui.activity

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import com.example.androidroomkotlin.R
import com.example.androidroomkotlin.database.Database
import com.example.androidroomkotlin.database.dao.AlunoDAO
import com.example.androidroomkotlin.model.Aluno
import com.example.androidroomkotlin.ui.activity.ConstantesActivities.CHAVE_ALUNO
import com.example.androidroomkotlin.ui.adapter.ListaAlunosAdapter

import kotlinx.android.synthetic.main.activity_lista_alunos.*

class ListaAlunosActivity : AppCompatActivity() {

    var TITULO_APPBAR = "Lista de alunos"

    private lateinit var dao: AlunoDAO
    private lateinit var adapter: ListaAlunosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)
        setSupportActionBar(toolbar)
        setTitle(TITULO_APPBAR)

        configuraFabNovoAluno()

        val database = Database.instance(this)
        dao = database.alunoDao()
        val alunosLiveData = dao.all()
        alunosLiveData.observe(this, Observer { alunos ->
            alunos?.let {
                adapter.replaceAllProducts(it)
            }
        })
        configuraLista()
    }

    private fun configuraFabNovoAluno() {
        fab.setOnClickListener { view ->
            abreFormularioModoInsereAluno()
        }
    }

    private fun abreFormularioModoInsereAluno() {
        var vaiParaFomularioActivity: Intent = Intent(this, FormularioAlunoActivity::class.java)
        startActivity(vaiParaFomularioActivity)
    }

    fun configuraLista(){
        this.adapter = ListaAlunosAdapter(context = this)

        var listaDeAlunos = findViewById<ListView>(R.id.activity_lista_alunos_listview);
        listaDeAlunos.adapter = this.adapter

        listaDeAlunos.setOnItemClickListener{ parent, view, position, id ->
            goToAlunoDetail(this.adapter.getItem(position))
        }

        listaDeAlunos.setOnCreateContextMenuListener { menu, _, _ ->
            menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val idDoMenu = item?.itemId

        if (idDoMenu == 1){
            var menuInfo: AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            var alunoEscolhido = this.adapter.getItem(menuInfo.position)
            dao.delete(alunoEscolhido)
        }
        return super.onContextItemSelected(item)
    }

    private fun goToAlunoDetail(aluno: Aluno) {
        val intent = Intent(this, FormularioAlunoActivity::class.java)
        intent.putExtra(CHAVE_ALUNO, aluno)
        startActivity(intent)
    }
}
