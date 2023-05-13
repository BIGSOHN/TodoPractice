package com.example.todopra.model.adapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.todopra.databinding.DialogEditBinding
import com.example.todopra.databinding.ListItemTodoBinding
import com.example.todopra.model.ToDoInfo
import java.text.SimpleDateFormat
import java.util.Date

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var lstTodo : ArrayList<ToDoInfo> = ArrayList()

    init {
        // 샘플 리스트 아이템 인스턴스 생성
        val todoItem = ToDoInfo()
        todoItem.todoContent = "컴퓨터 사용시간 줄이기"
        todoItem.todoDate = "2022-06-01 22:21"
        lstTodo.add(todoItem)

        // 샘플 리스트 아이템 인스턴스 생성
        val todoItem2 = ToDoInfo()
        todoItem2.todoContent = "컴퓨터 사용시간 줄이기"
        todoItem2.todoDate = "2022-06-01 22:22"
        lstTodo.add(todoItem2)

        // 샘플 리스트 아이템 인스턴스 생성
        val todoItem3 = ToDoInfo()
        todoItem3.todoContent = "컴퓨터 사용시간 줄이기"
        todoItem3.todoDate = "2022-06-01 22:23"
        lstTodo.add(todoItem3)


    }

    fun addListItem(todoItem: ToDoInfo) {
        // 인덱스 넣으면 아이템이 최신순서로 배치되서 갱신
        lstTodo.add(0, todoItem)
    }

    inner class TodoViewHolder(private val binding: ListItemTodoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(todoItem: ToDoInfo) {
            // 리스트 뷰 데이터를 UI에 연동
            binding.tvContent.setText(todoItem.todoContent)
            binding.tvDate.setText(todoItem.todoDate)

            // 리스트 삭제 버튼 클릭 연동
            binding.btnRemove.setOnClickListener {
                // 쓰레기통 이미지 클릭 시 내부 로직 수행

                AlertDialog.Builder(binding.root.context)
                    .setTitle("주의")
                    .setMessage("제거하시면 데이터는 복구되지 않습니다!\n 정말 제거하시겠습니까?")
                    .setPositiveButton("제거", DialogInterface.OnClickListener { dialog, which ->
                        lstTodo.remove(todoItem)
                        notifyDataSetChanged()

                        // 토스트 팝업 메시지 표시
                        Toast.makeText(binding.root.context, "제거 완료", Toast.LENGTH_SHORT).show()
                    })
                    .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->

                    })
                    .show()

            }

            // 리스트 수정 클릭 연동
            binding.root.setOnClickListener {
                val bindingDialog = DialogEditBinding.inflate(LayoutInflater.from(binding.root.context), binding.root, false)

                // 기존에 작성된 데이터 보여주기
                bindingDialog.etMemo.setText(todoItem.todoContent)

                AlertDialog.Builder(binding.root.context)
                    .setTitle("To-Do 남기기")
                    .setView(bindingDialog.root)
                    .setPositiveButton("작성완료", DialogInterface.OnClickListener { dialog, which ->
                        todoItem.todoContent = bindingDialog.etMemo.text.toString()
                        todoItem.todoDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                        // array list 수정
                        lstTodo.set(adapterPosition, todoItem)
                        notifyDataSetChanged() // 리스트 새로고침
                    })
                    .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->

                    })
                    .show()
            }

        }
    }

    // 뷰홀더가 생성됨. (각 리스트 아이템 1개씩 구성될 때마다 이 오버라이드 메소드가 호출 됨.)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        val binding = ListItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    // 뷰홀더가 바인딩 (결합) 이루어질 때 해줘야 될 처리들을 구현.
    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        holder.bind(lstTodo[position])
    }

    // 리스트 총 갯수
    override fun getItemCount(): Int {
        return lstTodo.size
    }

}