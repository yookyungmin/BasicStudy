import React, {useState} from "react";

function TodoList() {
    //할일 목록과 새로운 할일을 지정할 상자만들기
    const [todoList, setTodoList] = useState([]);
    //할일 목록을 저장하는 상자
    const [newTodo, setNewTodo] = useState("");
    //새로 추가할 목록을 저장하는 상자

    //새로운 할일을 추가하는 함수
    const addNewTodo = () =>{
        if(newTodo.trim() !== ""){ //newTodo 값이 비어있지않다면
            setTodoList([...todoList, newTodo])
            setNewTodo("")//입력필드를 바꿔줌
        }
    }

    //할일 삭제하는 함수
    const deleteTodo = (index)  =>{
        //선택한 할일을 제외하 새로운 목록 만들기
        const updatedList = [
            ...todoList.slice(0, inxdex), //index이전의 할일들을 가져온다.
            ...todoList.slice(index+1)
            //index이후의 순서로 할일들을 가져옴.
        ]
        setTodoList(updatedList)
    }
    return (
        <div>
            <h1 style={props.style}>To do List</h1>
            <input type="text" value={newTodo}

                   onChange={(e) => {
                       console.log(e);
                       console.log('newTodo:${newTodo}')
                       setNewTodo((e.target.value))
                   }}/>

            <button onClick={addNewTodo}>추가</button>
            <ul>
                {todoList.map((todo, index) => {
                    <li key={index}>
                        {todo}
                        <button onClick={() => deleteTodo(index)}>삭제</button>
                    </li>
                })}
            </ul>
        </div>
    )
}


export default TodoList;