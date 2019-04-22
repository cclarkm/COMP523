import React, { Component } from 'react';
import './App.css';

const URL = "http://localhost:8080/";
var TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWFjaGVydGVzdCIsImV4cCI6MTU1Njc1MjQ3M30.ewZPngGjEF5TfWlc2O1TzKiGHV-f19lC_D49VTc6qs5aiOnX1dmHUo-vNErx8HAm7E9jDb8iwTEcd-g5dTNDsw";

class App extends Component {
  constructor(props) {
    super(props);
    this.getStudents();

    //Assign state's default values
    this.state = {
      students: [],
      createNewVisibility: "hidden",
      popupVisibility: "hidden",
      moreVisibility: "hidden",
      studentSelected: {}
    };
  }

  getStudents = () => {
    fetch(URL + "student", {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({students: data.students});
      if (this.state.studentSelected !== {}) {
        for (let student of data.students) {
          if (student.id === this.state.studentSelected.id) {
            this.setState({"studentSelected": student});
            break;
          }
        }
      }
      console.log(data);
    }, (error) => {
      console.error(error);
    });
  }

  updateStudent = (id, data) => {
    fetch(URL + "student/" + id, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      },
      body: JSON.stringify(data)
    })
    .then((response) => {
      console.log(response);
      this.getStudents();
    }, (error) => {
      console.error(error);
    });
  }

  handleStudentRowClick = (student) => {
    this.setState({studentSelected: student});
  }

  handleStudentUpdate = (data) => {
    delete data.editable;
    this.updateStudent(this.state.studentSelected.id, data);
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
            <StudentTable className="StudentTable" students={this.state.students} onSelect={this.handleStudentRowClick} />
          </div>
          <div className="RightColumn">
            <StudentInfo className="StudentInfo" onUpdate={this.handleStudentUpdate} viewLogsHandler={this.viewLogsHandler} viewMoreHandler={this.viewMoreHandler} student={this.state.studentSelected} />
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
        {/* <div className="CreateNew">
          <button type="button" className="NewStudent" onClick={() => this.props.viewNewHandler(true)}>
            Create New Student
          </button>
        </div> */}
      </div>
    )
  }
}

class StudentTable extends Component {
  getStudentRows = () => {
    let listOfStudents = [];
    for (let i = 0; i < this.props.students.length; i++) {
      listOfStudents.push(
        <tr className="StudentRow" onClick={() => this.props.onSelect(this.props.students[i])}>
          <td>{this.props.students[i].firstName}</td>
          <td>{this.props.students[i].lastName}</td>
          <td>{this.props.students[i].permissionDate}</td>
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
  constructor(props) {
    super(props);
    this.state = {
      editable: false,
      firstName: this.props.student.firstName,
      lastName: this.props.student.lastName,
      dob: this.props.student.dob,
      gender: this.props.student.gender,
      raceEthnicity: this.props.student.raceEthnicity,
      hispanic: this.props.student.hispanic,
      grade: this.props.student.grade,
      serviceArea: this.props.student.serviceArea,
      county: this.props.student.county,
      district: this.props.student.district,
      school: this.props.student.school,
      label: this.props.student.label,
      currentTeacher: this.props.student.currentTeacher,
      secondTeacher: this.props.student.secondTeacher
    };
  }

  resetFormState = () => {
    this.setState({
      editable: false,
      firstName: this.props.student.firstName,
      lastName: this.props.student.lastName,
      dob: this.props.student.dob,
      gender: this.props.student.gender,
      raceEthnicity: this.props.student.raceEthnicity,
      hispanic: this.props.student.hispanic,
      grade: this.props.student.grade,
      serviceArea: this.props.student.serviceArea,
      county: this.props.student.county,
      district: this.props.student.district,
      school: this.props.student.school,
      label: this.props.student.label,
      currentTeacher: this.props.student.currentTeacher,
      secondTeacher: this.props.student.secondTeacher
    });
  }

  componentDidUpdate(oldProps) {
    const newProps = this.props;
    if(oldProps.student !== newProps.student) {
      this.resetFormState();
    }
  }

  componentDidMount() {
    this.resetFormState();
  }

  render() {
    return (
      <div className="StudentInfo">
        <div className="Heading">
          {(this.props.student.firstName !== undefined) ? <span>Student Info for <b>{this.props.student.firstName} {this.props.student.lastName}</b></span> : "No Student Selected"}
        </div>
        <table className="StudentInfoTable">
          <tbody>
            {this.state.editable ?
            <React.Fragment>
              <StudentInfoField label="First Name" value={this.state.firstName} onChange={(v) => this.setState({"firstName": v})} editable={this.state.editable} />
              <StudentInfoField label="Last Name" value={this.state.lastName} onChange={(v) => this.setState({"lastName": v})} editable={this.state.editable} />
            </React.Fragment>: ""}
            <StudentInfoField label="Date of Birth" value={this.state.dob} onChange={(v) => this.setState({"dob": v})} editable={this.state.editable} />
            <StudentInfoField label="Gender" value={this.state.gender} onChange={(v) => this.setState({"gender": v})} editable={this.state.editable} />
            <StudentInfoField label="Race" value={this.state.raceEthnicity} onChange={(v) => this.setState({"raceEthnicity": v})} editable={this.state.editable} />
            <StudentInfoField label="Hispanic" value={this.state.hispanic} onChange={(v) => this.setState({"hispanic": v})} editable={this.state.editable} />
            <StudentInfoField label="Grade" value={this.state.grade} onChange={(v) => this.setState({"grade": v})} editable={this.state.editable} />
            <StudentInfoField label="Service Area" value={this.state.serviceArea} onChange={(v) => this.setState({"serviceArea": v})} editable={this.state.editable} />
            <StudentInfoField label="County" value={this.state.county} onChange={(v) => this.setState({"county": v})} editable={this.state.editable} />
            <StudentInfoField label="District" value={this.state.district} onChange={(v) => this.setState({"district": v})} editable={this.state.editable} />
            <StudentInfoField label="School" value={this.state.school} onChange={(v) => this.setState({"school": v})} editable={this.state.editable} />
            <StudentInfoField label="Label" value={this.state.label} onChange={(v) => this.setState({"label": v})} editable={this.state.editable} />
            {/* <StudentInfoField label="IEP, 504, etc." value={this.props.student.dob} editable={this.state.editable} /> */}
            {/* <StudentInfoField label="Signed Release Date" value={this.props.student.dob} editable={this.state.editable} /> */}
            <StudentInfoField label="Primary Teacher" value={this.state.currentTeacher} onChange={(v) => this.setState({"currentTeacher": v})} editable={this.state.editable} />
            <StudentInfoField label="Secondary Teacher" value={this.state.secondTeacher} onChange={(v) => this.setState({"secondTeacher": v})} editable={this.state.editable} />
          </tbody>
        </table>
        <div className="ButtonHolder">
          <button type="button" className="SubmitButton" onClick={() => {
            let output = JSON.parse(JSON.stringify(this.state));
            delete output.editable;
            delete output.gender;
            delete output.raceEthnicity;
            delete output.serviceArea;
            delete output.school;
            delete output.district;
            delete output.county;
            delete output.grade;
            delete output.currentTeacher;
            delete output.secondTeacher;
            this.props.onUpdate(output);
            this.setState({editable: false});
            }} style={{"display": this.state.editable ? "block" : "none"}}>
            Submit Updated Info
          </button>
        </div>
        <div className="ButtonHolder">
          <button type="button" className="Button" onClick={() => {
            if (this.state.editable) {
              this.resetFormState();
            } else {
              this.setState({editable: true})
            }
            }}>
            {this.state.editable ? "Cancel Editing" : "Edit Student Information"}
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
              <td><input type="text" className="Multi1"></input></td>
              <td className="Text">through</td>
              <td><input type="text" className="Multi1"></input></td>
            </tr>
            <tr>
              <td><input type="text" className="Multi1"></input></td>
              <td className="Text">through</td>
              <td><input type="text" className="Multi1"></input></td>
            </tr>
            <tr>
              <td><input type="text" className="Multi1"></input></td>
              <td className="Text">through</td>
              <td><input type="text" className="Multi1"></input></td>
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

class StudentInfoField extends Component {
  render() {
    return (
      <tr>
        <td className="InputLabel">{this.props.label === undefined ? "" : this.props.label}</td>
        <td><input type="text" className="InputField" value={this.props.value} onChange={(e) => this.props.onChange(e.target.value)} readOnly={!this.props.editable} /></td>
      </tr>
    );
  }
}

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
              <Log />
              <Log />
              <Log />
          </table>
        </div>
      </div>
    );
  }
}

class Log extends Component {
  render() {
    return (
      <tr className="Log">
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
      </tr>
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
            <tbody>
              <tr>
                <td className="InputLabel">First Name</td>
                <td><input type="text" className="InputField" align="right"></input></td>
              </tr>
              <tr>
                <td className="InputLabel">Last Name</td>
                <td><input type="text" className="InputField" align="right"></input></td>
              </tr>
              <tr>
                <td className="InputLabel">Admit Date</td>
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
            </tbody>
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

export default App;
