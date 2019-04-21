import React, { Component } from 'react';
import './App.css';

class LogPopup extends Component {
  render() {
    let style = {
      'visibility': this.props.visibility
    };
    return (
      <div className="StudentPopup" style={style}>
        <div className="InnerPopup">
          <div className="PopupHeader">
            <button className="BackButton" onClick={() => this.props.viewLogsHandler(false)}>Go Back</button>
            <div className="PopTitle">View Logs for <b>Idrees Hassan</b></div>
          </div>
          <table className="PopTable">
            <tr>
              <Log />
              <Log />
              <Log />
            </tr>
          </table>
        </div>
      </div>
    );
  }
}

class Log extends Component {
  render() {
    return (
      <div className="Log">
        <div className="LogHeader">
          <div className="LogHeaderSection">
            <span className="PopSpan">Date of Log</span>
            <input className="PopInput" type="text"></input>
          </div>
          <div className="LogHeaderSection">
            <span className="PopSpan">Teacher</span>
            <input className="PopInput" type="text"></input>
          </div>
          <div className="LogHeaderSection">
            <span className="PopSpan">Type</span>
            <input className="PopInput" type="text"></input>
          </div>
        </div>
        <div className="LogBody">
          <textarea rows="7" cols="85" className="NoteInput" placeholder="Notes">
          </textarea>
          <button className="LogSubmit">Update</button>
        </div>
      </div>
    );
  }
}

class NewStudentPopup extends Component {
  render() {
    let style = {
      'visibility': this.props.visibility
    };
    return (
      <div className="StudentPopup" style={style}>
        <div className="InnerPopup">
          <div className="PopupHeader">
            <button className="BackButton" onClick={() => this.props.viewNewHandler(false)}>Go Back</button>
            <div className="PopTitle"><b className="NewTitle">Create New Student</b></div>
            <button className="BackButton"><b>Create</b></button>
          </div>
          <table className="PopTable">
            <tr>
              <td className="InputLabel">First Name</td>
              <td><input type="text" className="InputField" align="right"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Last Name</td>
              <td><input type="text" className="InputField" align="right"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Teacher</td>
              <td><input type="text" className="InputField" align="right"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Date of Birth</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Gender</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Race</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Hispanic</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Grade</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Service Area</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Country</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">District</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">School</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Label</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">IEP,504,etc.</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Signed Release Date</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Primary Teacher</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Secondary Teacher</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
          </table>
        </div>
      </div>
    );
  }
}

class PrevDatesPopup extends Component {
  render() {
    let style = {
      'visibility': this.props.visibility
    };
    return (
      <div className="StudentPopup" style={style}>
        <div className="InnerPopup">
          <div className="PopupHeader">
            <button className="BackButton" onClick={() => this.props.viewMoreHandler(false)}>Go Back</button>
            <div className="PopTitle">Previous Admissions</div>
          </div>
          <table className="PopTable">
            <tr>
              <Dates />
              <Dates />
              <Dates />
              <Dates />
              <Dates />
              <Dates />
              <Dates />
              <Dates />
              <Dates />
              <Dates />
              <Dates />
            </tr>
          </table>
        </div>
      </div>
    );
  }
}

class Dates extends Component {
  render() {
    return (
      <div className="Log">
            <td><input type="text" className="Multi1"></input></td>
            <td className="Text">through</td>
            <td><input type="text" className="Multi2"></input></td>
      </div>
    );
  }
}

class App extends Component {
  constructor(props) {
    super(props);

    //Assign state's default values
    this.state = {
      students: getStudents(),
      createNewVisibility: "hidden",
      popupVisibility: "hidden",
      moreVisibility: "hidden"
    };
  }

  viewNewHandler = (show) => {
    if (show) {
      this.setState({ "createNewVisibility": "visible" });
    } else {
      this.setState({ "createNewVisibility": "hidden" });
    }
  }

  viewLogsHandler = (show) => {
    if (show) {
      this.setState({ "popupVisibility": "visible" });
    } else {
      this.setState({ "popupVisibility": "hidden" });
    }
  }

  viewMoreHandler = (show) => {
    if (show) {
      this.setState({ "moreVisibility": "visible" });
    } else {
      this.setState({ "moreVisibility": "hidden" });
    }
  }

  render() {
    return (
      <div className="App">
        <div className="FlexColumns">
          <div className="LeftColumn">
            <FilterStudent className="FilterStudents" viewNewHandler={this.viewNewHandler} />
            <StudentTable className="StudentTable"
              students={this.state.students} />
          </div>
          <div className="RightColumn">
            <StudentInfo className="StudentInfo" viewLogsHandler={this.viewLogsHandler} viewMoreHandler={this.viewMoreHandler} />
            <NewStudentPopup visibility={this.state.createNewVisibility} viewNewHandler={this.viewNewHandler} />
            <LogPopup visibility={this.state.popupVisibility} viewLogsHandler={this.viewLogsHandler} />
            <PrevDatesPopup visibility={this.state.moreVisibility} viewMoreHandler={this.viewMoreHandler} />
          </div>
        </div>
      </div>
    );
  }
}

class FilterStudent extends Component {
  render() {
    return (
      <div className="FilterStudents">
        <div className="Filter">
          <div className="FilterHeading">
            Filter Students
        </div>
          <table className="StudentFilterTable">
            <tbody>
              <tr>
                <td className="LeftInputLabel">First Name</td>
                <td><input type="text" className="InputField" align="right"></input></td>
              </tr>
              <tr>
                <td className="LeftInputLabel">Last Name</td>
                <td><input type="text" className="InputField" align="right"></input></td>
              </tr>
              <tr>
                <td className="LeftInputLabel">Teacher</td>
                <td><input type="text" className="InputField" align="right"></input></td>
              </tr>
            </tbody>
          </table>
        </div>
        <div className="CreateNew">
          <button type="button" className="NewStudent" onClick={() => this.props.viewNewHandler(true)}>
            Create New Student
          </button>
        </div>
      </div>
    )
  }
}

class StudentTable extends Component {
  getStudentRows = () => {
    let listOfStudents = [];
    for (let i = 0; i < this.props.students.length; i++) {
      listOfStudents.push(
        <tr className="StudentRow">
          <td>{this.props.students[i].firstName}</td>
          <td>{this.props.students[i].lastName}</td>
          <td>{this.props.students[i].admitDate}</td>
        </tr>
      );
    }
    return listOfStudents;
  }

  render() {
    return (
      <div className="StudentTableContainer">
        <table className="StudentTable">
          <thead>
            <tr className="StudentTableHeader">
              <td className="StudentLeft">First Name</td>
              <td className="StudentCenter">Last Name</td>
              <td className="StudentRight">Admit Date</td>
            </tr>
          </thead>
          <tbody>
            {this.getStudentRows()}
          </tbody>
        </table>
      </div>
      //<span>{this.createStudentHTMLElements()}</span>
    );
  }
}

class StudentInfo extends Component {
  render() {
    return (
      <div className="StudentInfo">
        <div className="Heading">
          Student Info for <b>Idrees Hassan</b>
        </div>
        <table className="StudentInfoTable">
          <tbody>
            <tr>
              <td className="InputLabel">Date of Birth</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Gender</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Race</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Hispanic</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Grade</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Service Area</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Country</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">District</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">School</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Label</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">IEP,504,etc.</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Signed Release Date</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Primary Teacher</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="InputLabel">Secondary Teacher</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
          </tbody>
        </table>
        <div className="ButtonHolder">
          <button type="button" className="Button">
            Edit Student Information
          </button>
          <button type="button" className="Button" onClick={() => this.props.viewLogsHandler(true)}>
            View Student Logs
          </button>
        </div>
        <div className="SmallHeading">
          Admissions Info
        </div>
        <div className="SpecialHeading">
          <span>Total Prior Admissions: <b>42</b></span>
        </div>
        <div className="TableHeading">
          Current Admissions Dates
        </div>
        <table className="DateTable">
          <tbody>
            <tr>
              <td className="LeftInputLabel">Date Admitted</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
            <tr>
              <td className="LeftInputLabel">Date Discharged</td>
              <td><input type="text" className="InputField"></input></td>
            </tr>
          </tbody>
        </table>
        <div className="TableHeading">
          Previous Admissions
        </div>
        <table className="AdmissionTable">
          <tbody>
            <tr>
              <td><input type="text" className="Multi"></input></td>
              <td className="Text">through</td>
              <td><input type="text" className="Multi"></input></td>
            </tr>
            <tr>
              <td><input type="text" className="Multi"></input></td>
              <td className="Text">through</td>
              <td><input type="text" className="Multi"></input></td>
            </tr>
            <tr>
              <td><input type="text" className="Multi"></input></td>
              <td className="Text">through</td>
              <td><input type="text" className="Multi"></input></td>
            </tr>
          </tbody>
        </table>
        <div style={{ "width": "60%", "display": "flex", "margin": "0 auto" }}>
          <button type="button" className="SmallButton" onClick={() => this.props.viewMoreHandler(true)}>
            View More
          </button>
        </div>
      </div>
    );
  }
}

function getStudents() {
  return [
    {
      "firstName": "Idrees",
      "lastName": "Hassan",
      "admitDate": "10/21/1997"
    },
    {
      "firstName": "Paul",
      "lastName": "Song",
      "admitDate": "10/22/1997"
    }
  ];
}

export default App;
