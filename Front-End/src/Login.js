import React, { Component } from 'react';
import './Login.css';



class App extends Component {
  constructor(props) {
    super(props);

    //Assign state's default values
    this.state = {

    };
  }

  

  render() {
    return (
      <div className="App">
      
          <div className="Title">
            UNC Hospital School
          </div>
          <div className="Panel">
             <FilterStudent className="FilterStudents" />
          </div>
        </div>
    
    );
  }
}

class FilterStudent extends Component {
  render() {
    return (
      <div className="Login">
        <div className="Filter">
          <div className="Heading">
            Enter Login Info
          </div>
          <table className="Table">
            <tbody>
              <tr>
                <td className="LeftInputLabel">
                  Username
                  <input type="text" className="InputFieldUser"></input>
                </td>
              </tr>
              <tr>
                <td className="LeftInputLabel">
                  Password
                  <input type="text" className="InputFieldPassword"></input>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        {<div className="CreateNew">
          <button type="button" className="Signin">
            Sign In
          </button>
        </div> }
        {/*<div>
          <button type="button" className="BottomButton">Create Account</button>
          <button type="button" className="BottomButton">Change Password</button>
        </div>*/}
      </div>
    )
  }
}

export default App;
