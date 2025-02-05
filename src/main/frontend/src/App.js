import logo from './logo.svg';
import './App.css';
import {useState} from "react";
import './style.css';
import TodoList from './components/TodoList.js';

function App() {


  return (

      <TodoList style={{color: 'bule'}}/>
      // <div>
      //   <h1>To do List</h1>
      //   <input type="text" value={newTodo}
      //
      //          onChange={(e) => {
      //              console.log(e);
      //              console.log('newTodo:${newTodo}')
      //              setNewTodo((e.target.value))}}/>
      //
      //     <button onClick={addNewTodo}>추가</button>
      //   <ul>
      //       {todoList.map((todo, index) =>{
      //           <li key={index}>
      //               {todo}
      //               <button onClick={() => deleteTodo(index)}>삭제</button>
      //           </li>
      //       })}
      //   </ul>
      // </div>
    // <div className="App">
    //   <header className="App-header">
    //     <img src={logo} className="App-logo" alt="logo" />
    //     <p>
    //       Edit <code>src/App.js</code> and save to reload.
    //     </p>
    //     <a
    //       className="App-link"
    //       href="https://reactjs.org"
    //       target="_blank"
    //       rel="noopener noreferrer"
    //     >
    //       Learn React
    //     </a>
    //   </header>
    // </div>
  );
}

export default App;
