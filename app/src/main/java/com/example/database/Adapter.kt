package com.example.database

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_rv.view.*

class TodoAdapter(
    private val todos : MutableList<Task>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_rv,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: Task) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }
    fun deleteDoneTodos() {
        todos.removeAll {
                todo -> todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else{
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }


    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTask = todos[position]
        holder.itemView.apply {
            tvTodoTitle.text = curTask.word
            cbDone.isChecked = curTask.isChecked
            toggleStrikeThrough(tvTodoTitle, curTask.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tvTodoTitle, isChecked)
                curTask.isChecked = !curTask.isChecked
            }
        }

    }

    override fun getItemCount(): Int {
        return todos.size
    }
}