import React, { Component } from 'react';
import './App.css';

const URL = "http://localhost:8080/";
var TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWFjaGVydGVzdCIsImV4cCI6MTU1Njc1MjQ3M30.ewZPngGjEF5TfWlc2O1TzKiGHV-f19lC_D49VTc6qs5aiOnX1dmHUo-vNErx8HAm7E9jDb8iwTEcd-g5dTNDsw";

class App extends Component {
  constructor(props) {
    super(props);
    this.getStudents();
    this.getGenders();
    this.getRaces();
    this.getGrades();
    this.getServiceAreas();
    this.getCounties();
    this.getDistricts();
    this.getSchools();
    this.getTeachers();
    this.getLogTypes();

    //Assign state's default values
    this.state = {
      students: [],
      createNewVisibility: "hidden",
      popupVisibility: "hidden",
      moreVisibility: "hidden",
      currentStudent: {},
      currentStudentLogs: [],
      currentStudentAdmissions: [],
      firstNameFilter: "",
      lastNameFilter: "",
      teacherFilter: "",
      showRightColumn: false,
      genders: [],
      races: [],
      grades: [],
      serviceAreas: [],
      counties: [],
      districts: [],
      schools: [],
      teachers: [],
      logTypes: []
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
      if (this.state.currentStudent !== {}) {
        for (let student of data.students) {
          if (student.id === this.state.currentStudent.id) {
            this.setState({"currentStudent": student});
            break;
          }
        }
      }
    }, (error) => {
      console.error(error);
    });
  }

  getLogs = (id) => {
    fetch(URL + "visitInformation/" + id, {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({currentStudentLogs: data.visits});
    }, (error) => {
      console.error(error);
    });
  }

  getAdmissions = (id) => {
    fetch(URL + "admission/" + id, {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({currentStudentAdmissions: data.admissions});
    }, (error) => {
      console.error(error);
    });
  }

  getGenders = () => {
    fetch(URL + "gender", {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({genders: data.genders});
    }, (error) => {
      console.error(error);
    });
  }

  getRaces = () => {
    fetch(URL + "raceEth", {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({races: data.raceEths});
    }, (error) => {
      console.error(error);
    });
  }

  getGrades = () => {
    fetch(URL + "grade", {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({grades: data.grades});
    }, (error) => {
      console.error(error);
    });
  }

  getServiceAreas = () => {
    fetch(URL + "serviceArea", {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({serviceAreas: data.serviceAreas});
    }, (error) => {
      console.error(error);
    });
  }

  getCounties = () => {
    fetch(URL + "county", {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({counties: data.counties});
    }, (error) => {
      console.error(error);
    });
  }

  getDistricts = () => {
    fetch(URL + "district", {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({districts: data.districts});
    }, (error) => {
      console.error(error);
    });
  }

  getSchools = () => {
    fetch(URL + "school", {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({schools: data.schools});
    }, (error) => {
      console.error(error);
    });
  }

  getSchools = () => {
    fetch(URL + "school", {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({schools: data.schools});
    }, (error) => {
      console.error(error);
    });
  }

  getTeachers = () => {
    fetch(URL + "teacher", {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({teachers: data.teachers});
    }, (error) => {
      console.error(error);
    });
  }

  getLogTypes = () => {
    fetch(URL + "logType", {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      }
    })
    .then(response => response.json())
    .then((data) => {
      this.setState({logTypes: data.logTypes});
    }, (error) => {
      console.error(error);
    });
  }

  updateStudent = (id, data) => {
    fetch(URL + "student/" + id, {
      method: 'PUT',
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

  updateLog = (id, data) => {
    fetch(URL + "visitInformation/" + id, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      },
      body: JSON.stringify(data)
    })
    .then((response) => {
      console.log(response);
      this.getLogs(this.state.currentStudent.id);
    }, (error) => {
      console.error(error);
    });
  }

  createLog = (data) => {
    fetch(URL + "visitInformation", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': TOKEN
      },
      body: JSON.stringify(data)
    })
    .then((response) => {
      console.log(response);
      this.getLogs(this.state.currentStudent.id);
    }, (error) => {
      console.error(error);
    });
  }

  handleStudentRowClick = (student) => {
    this.setState({
      currentStudent: student,
      showRightColumn: true
    });
    this.getLogs(student.id);
    this.getAdmissions(student.id);
  }

  handleStudentUpdate = (data) => {
    delete data.editable;
    this.updateStudent(this.state.currentStudent.id, data);
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

  handleFirstNameFilter = (filter) => {
    this.setState({"firstNameFilter": filter});
  }

  handleLastNameFilter = (filter) => {
    this.setState({"lastNameFilter": filter});
  }

  handleTeacherFilter = (filter) => {
    this.setState({"teacherFilter": filter});
  }

  handleLogSubmit = (logState) => {
    console.log({
      "sid": this.state.currentStudent.id,
      "dov": logState.dov,
      "notes": logState.notes,
      "tid": 2,
      "logType": 5
    });
    this.createLog({
      "sid": this.state.currentStudent.id,
      "dov": logState.dov,
      "notes": logState.notes,
      "tid": 2,
      "logType": 5
    });
  }

  handleLogUpdate = (previousLog, logState) => {
    this.updateLog(previousLog.id, {
      "notes": logState.notes
    });
  }

  render() {
    return (
      <div className="App">
        <div className="FlexColumns">
          <div className="LeftColumn">
            <FilterStudent className="FilterStudents" viewNewHandler={this.viewNewHandler} onFirstNameChange={this.handleFirstNameFilter} onLastNameChange={this.handleLastNameFilter} onTeacherChange={this.handleTeacherFilter}/>
            <StudentTable className="StudentTable" currentStudent={this.state.currentStudent} students={this.state.students} onSelect={this.handleStudentRowClick} firstNameFilter={this.state.firstNameFilter} lastNameFilter={this.state.lastNameFilter} teacherFilter={this.state.teacherFilter}/>
          </div>
          <div className="RightColumn" style={this.state.showRightColumn ? {"width": "50%"} : {}}>
            <StudentInfo className="StudentInfo"
            admissions={this.state.currentStudentAdmissions}
            genders={this.state.genders}
            races={this.state.races}
            grades={this.state.grades}
            serviceAreas={this.state.serviceAreas}
            counties={this.state.counties}
            districts={this.state.districts}
            schools={this.state.schools}
            teachers={this.state.teachers}
            logTypes={this.state.logTypes}
            onUpdate={this.handleStudentUpdate} viewLogsHandler={this.viewLogsHandler} viewMoreHandler={this.viewMoreHandler} student={this.state.currentStudent} />
            <NewStudentPopup visibility={this.state.createNewVisibility} viewNewHandler={this.viewNewHandler} />
            <LogPopup student={this.state.currentStudent} visibility={this.state.popupVisibility} viewLogsHandler={this.viewLogsHandler} logs={this.state.currentStudentLogs} onLogSubmit={this.handleLogSubmit} onLogUpdate={this.handleLogUpdate} />
            <AdmissionsPopup visibility={this.state.moreVisibility} admissions={this.state.currentStudentAdmissions} viewMoreHandler={this.viewMoreHandler} />
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
                <td><input type="text" className="InputField" align="right" onChange={(e) => this.props.onFirstNameChange(e.target.value)}></input></td>
              </tr>
              <tr>
                <td className="LeftInputLabel">Last Name</td>
                <td><input type="text" className="InputField" align="right" onChange={(e) => this.props.onLastNameChange(e.target.value)}></input></td>
              </tr>
              <tr>
                <td className="LeftInputLabel">Teacher</td>
                <td><input type="text" className="InputField" align="right" onChange={(e) => this.props.onTeacherChange(e.target.value)}></input></td>
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
      if (this.props.students[i].firstName.toLowerCase().startsWith(this.props.firstNameFilter.toLowerCase())
       && this.props.students[i].lastName.toLowerCase().startsWith(this.props.lastNameFilter.toLowerCase())
       && (this.props.teacherFilter === ""
       || this.props.students[i].currentTeacher.toLowerCase().startsWith(this.props.teacherFilter.toLowerCase())
       || this.props.students[i].currentTeacher.toLowerCase().split(" ")[0].startsWith(this.props.teacherFilter.toLowerCase())
       || this.props.students[i].currentTeacher.toLowerCase().split(" ")[this.props.students[i].currentTeacher.toLowerCase().split(" ").length - 1].startsWith(this.props.teacherFilter.toLowerCase()))) {
        let style = {};
        if (this.props.currentStudent.id !== undefined && this.props.currentStudent.id === this.props.students[i].id) {
          style = {
            "border-style": "solid",
            "background": "rgba(126, 197, 255, 0.3)",
            'cursor': "auto"
          };
        }
        listOfStudents.push(
          <tr className="StudentRow" style={style} onClick={() => this.props.onSelect(this.props.students[i])}>
            <td>{this.props.students[i].firstName}</td>
            <td>{this.props.students[i].lastName}</td>
            <td>{this.props.students[i].currentTeacher}</td>
            <td  className="StudentRowDate">{this.props.students[i].permissionDate}</td>
          </tr>
        );
      }
    }
    return listOfStudents;
  }

  render() {
    return (
      <div className="StudentTableContainer">
        <table className="StudentTable">
          <thead>
            <tr className="StudentTableHeader">
              <td>First Name</td>
              <td>Last Name</td>
              <td>Current Teacher</td>
              <td>Admit Date</td>
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
  getID = (table, textKey, idKey, currentText) => {
    for (let i = 0; i < table.length; i++) {
      if (table[i][textKey] === currentText) {
        return table[i][idKey];
      }
    }
  }

  getTeacherID = (table, name) => {
    for (let i = 0; i < table.length; i++) {
      if (table[i]["firstName"] + " " + table[i]["lastName"] === name) {
        return table[i].tid;
      }
    }
  }

  getCleanState = () => {
    return {
      editable: false,
      firstName: this.props.student.firstName,
      lastName: this.props.student.lastName,
      dob: this.props.student.dob,
      gender: this.getID(this.props.genders, "gender", "gid", this.props.student.gender),
      raceEthnicity: this.getID(this.props.races, "raceEth", "rid", this.props.student.raceEthnicity),
      hispanic: this.props.student.hispanic,
      grade: this.getID(this.props.grades, "grade", "gid", this.props.student.grade),
      serviceArea: this.getID(this.props.serviceAreas, "field1", "sid", this.props.student.serviceArea),
      county: this.getID(this.props.counties, "county", "cid", this.props.student.county),
      district: this.getID(this.props.districts, "district", "did", this.props.student.district),
      school: this.getID(this.props.schools, "school", "sid", this.props.student.school),
      label: this.props.student.label,
      currentTeacher: this.getTeacherID(this.props.teachers, this.props.student.currentTeacher),
      secondTeacher: this.getTeacherID(this.props.teachers, this.props.student.secondTeacher),
    };
  }

  constructor(props) {
    super(props);
    this.state = this.getCleanState();
  }

  resetFormState = () => {
    this.setState(this.getCleanState());
  }

  getAdmissionRows = (limit = -1) => {
    let listOfAdmissions = [];
    if (limit === -1) {
      limit = this.props.admissions.length;
    }
    for (let i = 0; i < Math.min(this.props.admissions.length, limit); i++) {
      listOfAdmissions.push(
        <DateRange start={this.props.admissions[i].admissionDate} end={this.props.admissions[i].dischargeDate} />
      );
    }
    return listOfAdmissions;
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
          {(this.props.student.firstName !== undefined) ? <span>Student <b>{this.props.student.firstName} {this.props.student.lastName}</b></span> : "No Student Selected"}
        </div>
        <table className="StudentInfoTable">
          <tbody>
            {this.state.editable ?
            <React.Fragment>
              <StudentInfoField label="First Name" value={this.state.firstName} onChange={(v) => this.setState({"firstName": v})} editable={this.state.editable} />
              <StudentInfoField label="Last Name" value={this.state.lastName} onChange={(v) => this.setState({"lastName": v})} editable={this.state.editable} />
            </React.Fragment>: ""}
            <StudentInfoField label="Date of Birth" value={this.state.dob} onChange={(v) => this.setState({"dob": v})} editable={this.state.editable} />
            <StudentInfoSelect label="Gender" value={this.state.gender} options={this.props.genders.map(o => {return {"text": o.gender, "value": o.gid}})} onChange={(v) => this.setState({"gender": v})} editable={this.state.editable} />
            <StudentInfoSelect label="Race" value={this.state.raceEthnicity} options={this.props.races.map(o => {return {"text": o.raceEth, "value": o.rid}})} onChange={(v) => this.setState({"raceEthnicity": v})} editable={this.state.editable} />
            <StudentInfoSelect label="Hispanic" value={this.state.hispanic} options={[{"text": "Yes", "value": "true"}, {"text": "No", "value": "false"}]} onChange={(v) => this.setState({"hispanic": v})} editable={this.state.editable} />
            <StudentInfoSelect label="Grade" value={this.state.grade} options={this.props.grades.map(o => {return {"text": o.grade, "value": o.gid}})} onChange={(v) => this.setState({"grade": v})} editable={this.state.editable} />
            <StudentInfoSelect label="Service Area" value={this.state.serviceArea} options={this.props.serviceAreas.map(o => {return {"text": o.field1, "value": o.sid}})} onChange={(v) => this.setState({"serviceArea": v})} editable={this.state.editable} />
            <StudentInfoSelect label="County" value={this.state.county} options={this.props.counties.map(o => {return {"text": o.county, "value": o.cid}})} onChange={(v) => this.setState({"county": v})} editable={this.state.editable} />
            <StudentInfoSelect label="District" value={this.state.district} options={this.props.districts.map(o => {return {"text": o.district, "value": o.did}})} onChange={(v) => this.setState({"district": v})} editable={this.state.editable} />
            <StudentInfoSelect label="School" value={this.state.school} options={this.props.schools.map(o => {return {"text": o.school, "value": o.sid}})} onChange={(v) => this.setState({"school": v})} editable={this.state.editable} />
            <StudentInfoField label="Label" value={this.state.label} onChange={(v) => this.setState({"label": v})} editable={this.state.editable} />
            {/* <StudentInfoField label="IEP, 504, etc." value={this.props.student.dob} editable={this.state.editable} /> */}
            {/* <StudentInfoField label="Signed Release Date" value={this.props.student.dob} editable={this.state.editable} /> */}
            {/* <StudentInfoField label="Primary Teacher" value={this.state.currentTeacher} onChange={(v) => this.setState({"currentTeacher": v})} editable={this.state.editable} /> */}
            <StudentInfoSelect label="Primary Teacher" value={this.state.currentTeacher} options={this.props.teachers.map(o => {return {"text": o.firstName + " " + o.lastName, "value": o.tid}})} onChange={(v) => this.setState({"currentTeacher": v})} editable={this.state.editable} />
            {/* <StudentInfoField label="Secondary Teacher" value={this.state.secondTeacher} onChange={(v) => this.setState({"secondTeacher": v})} editable={this.state.editable} /> */}
            <StudentInfoSelect label="Secondary Teacher" value={this.state.secondTeacher} options={this.props.teachers.map(o => {return {"text": o.firstName + " " + o.lastName, "value": o.tid}})} onChange={(v) => this.setState({"secondTeacher": v})} editable={this.state.editable} />
          </tbody>
        </table>
        <div className="ButtonHolder">
          <button type="button" className="SubmitButton" onClick={() => {
            let output = JSON.parse(JSON.stringify(this.state));
            delete output.editable;
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
          <span>Total Prior Admissions: <b>{this.props.admissions.length}</b></span>
        </div>
        {/* <div className="TableHeading">
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
        </table> */}
        <div style={{"visibility": this.props.admissions.length > 0 ? "visible" : "hidden"}}>
          <div className="TableHeading">
            Recent Admissions
          </div>
          <table className="AdmissionTable">
            <tbody>
            {this.getAdmissionRows(2)}
            </tbody>
          </table>
          <div style={{ "width": "60%", "display": "flex", "margin": "0 auto" }}>
            <button type="button" className="Button" onClick={() => this.props.viewMoreHandler(true)}>
              View All Admissions
            </button>
          </div>
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

class StudentInfoSelect extends Component {

  getOptions = () => {
    let listOfOptions = [];
    for (let i = 0; i < this.props.options.length; i++) {
      listOfOptions.push(
        <option value={this.props.options[i].value}>{this.props.options[i].text}</option>
      );
    }
    return listOfOptions;
  }

  render() {
    return (
      <tr>
        <td className="InputLabel">{this.props.label === undefined ? "" : this.props.label}</td>
        <td>
          <select value={this.props.value} className="InputSelect" onChange={(e) => this.props.onChange(e.target.value)} disabled={!this.props.editable}>
            {this.getOptions()}
          </select>
        </td>
      </tr>
    );
  }
}

class LogPopup extends Component {
  getLogs = () => {
    let listOfLogs = [];
    listOfLogs.push(<Log log={{"dov": "", "teacher": "", "notes": "", "type": "General"}} new={true} onSubmit={this.props.onLogSubmit} onUpdate={this.props.onLogUpdate}/>);
    console.log(this);
    for (let i = 0; i < this.props.logs.length; i++) {
      let log = this.props.logs[i];
      listOfLogs.push(<Log log={log} new={false} onSubmit={this.props.onLogSubmit} onUpdate={this.props.onLogUpdate} />);
    }
    return listOfLogs;
  }

  render() {
    let style = {
      'visibility': this.props.visibility
    };
    return (
      <div className="StudentPopup" style={style}>
        <div className="InnerPopup">
          <div className="PopupHeader">
            <button className="BackButton" onClick={() => this.props.viewLogsHandler(false)}>Go Back</button>
            <div className="PopTitle">View Logs for <b>{this.props.student.firstName + " " + this.props.student.lastName}</b></div>
          </div>
          <table className="PopTable">
            {this.getLogs()}
          </table>
        </div>
      </div>
    );
  }
}

class Log extends Component {
  constructor(props) {
    super(props);
    this.state = {
      dov: "",
      teacher: "",
      logType: "",
      notes: ""
    };
  }

  render() {
    return (
      <tr className="Log">
        <div className="LogHeader">
          <div className="LogHeaderSection">
            <span className="PopSpan">Date of Log</span>
            <input className="PopInput" type="text" defaultValue={this.props.log.dov} onChange={(e) => this.setState({"dov": e.target.value})}></input>
          </div>
          <div className="LogHeaderSection">
            <span className="PopSpan">Teacher</span>
            <input className="PopInput" type="text" defaultValue={this.props.log.teacher} onChange={(e) => this.setState({"teacher": e.target.value})}></input>
          </div>
          <div className="LogHeaderSection">
            <span className="PopSpan">Type</span>
            <input className="PopInput" type="text" defaultValue={this.props.log.logType} onChange={(e) => this.setState({"logType": e.target.value})}></input>
          </div>
        </div>
        <div className="LogBody">
          <textarea rows="7" cols="85" className="NoteInput" placeholder="Notes" defaultValue={this.props.log.notes}  onChange={(e) => this.setState({"notes": e.target.value})}>
          </textarea>
          <button className="LogSubmit" onClick={() => {
            if (this.props.new) {
              this.props.onSubmit(this.state);
            } else {
              this.props.onUpdate(this.props.log, this.state);
            }
          }}>
            {this.props.new ? "New Log" : "Update"}
          </button>
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

class AdmissionsPopup extends Component {
  getAdmissionRows = () => {
    let listOfAdmissions = [];
    for (let i = 0; i < this.props.admissions.length; i++) {
      listOfAdmissions.push(
        <DateRange start={this.props.admissions[i].admissionDate} end={this.props.admissions[i].dischargeDate} />
      );
    }
    return listOfAdmissions;
  }

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
            <tbody>
              {this.getAdmissionRows()}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

class DateRange extends Component {
  render() {
    return (
      <tr className="AdmissionTableRow">
        <td><input type="text" className="AdmissionField" value={this.props.start}></input></td>
        <td className="AdmissionText">through</td>
        <td><input type="text" className="AdmissionField" value={this.props.end}></input></td>
      </tr>
    );
  }
}

export default App;
