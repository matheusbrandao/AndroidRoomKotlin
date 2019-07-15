package com.example.androidroomkotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.androidroomkotlin.R
import com.example.androidroomkotlin.model.Aluno
import kotlinx.android.synthetic.main.item_aluno.view.*

class ListaAlunosAdapter(
    private val alunos: MutableList<Aluno> = mutableListOf(),
    private val context: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return alunos.size
    }

    override fun getItem(position: Int): Aluno {
        return alunos[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewCriada: View = criarView(parent)
        var aluno = alunos[position]

        vincula(viewCriada, aluno)
        return viewCriada
    }

    private fun vincula(view: View, aluno: Aluno) {
        view.item_aluno_nome.text = aluno.nome
        view.item_aluno_telefone.text = aluno.telefone
    }

    private fun criarView(parent: ViewGroup?): View {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_aluno, parent, false)
    }

    fun replaceAllProducts(products: List<Aluno>) {
        this.alunos.clear()
        this.alunos.addAll(products)
        notifyDataSetChanged()
    }
}