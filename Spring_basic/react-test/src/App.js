import logo from './logo.svg';
import './App.css';



import React, { useState } from 'react';
import axios from 'axios';


function App() {

  const test = () => {
    axios.post(
      'http://localhost:8080/kdt_war_exploded/api/test',
      {
        headers:
          { 'Content-Type': 'application/json' }
      }
    ).then(
        res => {
          console.log(res)
        }
      )
  }

  const test2 = () => {
    axios.post('http://localhost:8080/kdt_war_exploded/api/test').then(
      res => {
        console.log(res)
      }
    )
  }

  const [id, setId] = useState("");

  const idHandler = (event) => {
    setId(event.currentTarget.value);
  }

  const searchUser = () => {
    axios.get('http://localhost:8080/kdt_war_exploded/api/v1/customers/' + id)
      .then(
        res => { console.log(res) }
      )
  }

  return (
    <div className="App">
      <br /><br /><br /><br /><br />
      
      <button onClick={test2}>POST 단순요청</button>

      <br /><br /><br />

      <button onClick={test}>POST 예비요청</button>


      <br /><br /><br />

      <input type="text" name="id" placeholder="customerId 입력" onChange={idHandler}></input>
      <button onClick={searchUser}>유저검색</button>

    </div>
  );
}

export default App;
