import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Swal from 'sweetalert2'

const StudentAdd = () => {
    const entity = "student";

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [cv, setCv] = useState('');
    const [isCurrentlyEmployed, setIsCurrentlyEmployed] = useState('');
    const [isPending, setIsPending] = useState(false);
    const navigate = useNavigate();

    const handleStudentCreate = async (e) => {
        e.preventDefault();
        setIsPending(true);
    
        try {
          // convert the cv to base64
          const base64CV = await convertFileToBase64(cv);

          const student = { firstName, lastName, email, phone, cv: base64CV, isCurrentlyEmployed };
          await addStudent(student);
          Swal.fire({
            title: `${entity} added successfully`,
            text: `Would you like to add another ${entity}?`,
            icon: 'success',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, I want to add another'
          }).then((result) => {
            if (!result.isConfirmed) {
              navigate('/');
            }
            resetForm();
          })
        } catch (error) {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: `An error occurred while adding the ${entity}, please try again`,
          })
          console.error(error);
        } finally {
          setIsPending(false);
        }
    }

    const convertFileToBase64 = (file) => {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => {
          const base64String = reader.result.split(',')[1];
          resolve(base64String);
        }
        reader.onerror = (error) => {
          reject(error);
        };
        reader.readAsDataURL(file);
      })
    }

    const addStudent = async (student) => {
        await axios.post("/students", student);
    }

    const resetForm = () => {
        setFirstName('');
        setLastName('');
        setEmail('');
        setPhone('');
        setCv('');
        setIsCurrentlyEmployed('');
      }

    return ( 
        <>
        <div id="createCourseDiv" className="container">
            <h2>Create {entity}</h2>

            <div className="mt-5">

            <form id="createCourseForm" onSubmit={handleStudentCreate}>

                <div className="mb-3">
                <label htmlFor="firstNameInput" className="form-label">First Name</label>
                <input type="text" className="form-control" id="firstNameInput" value={firstName} onChange={e => setFirstName(e.target.value)} required />
                </div>

                <div className="mb-3">
                <label htmlFor="lastNameInput" className="form-label">Last Name</label>
                <input type="text" className="form-control" id="lastNameInput" value={lastName} onChange={e => setLastName(e.target.value)} required />
                </div>
                
                <div className="mb-3">
                <label htmlFor="emailInput" className="form-label">Email</label>
                <input type="text" className="form-control" id="emailInput" value={email} onChange={e => setEmail(e.target.value)} required />
                </div>

                <div className="mb-3">
                <label htmlFor="phoneInput" className="form-label">Phone</label>
                <input type="text" className="form-control" id="phoneInput" value={phone} onChange={e => setPhone(e.target.value)} required />
                </div>

                <div className="mb-3">
                <label htmlFor="employmentStatusInput" className="form-label">Employment status</label>
                <select className="custom-select d-block w-100" id="employmentStatusInput" value={isCurrentlyEmployed} onChange={e => setIsCurrentlyEmployed(e.target.value)}>
                    <option value="select one">Select</option>
                    <option value="true">Yes, I am currently employed</option>
                    <option value="false">No, I am not currently employed</option>
                  </select>
                </div>

                <div className="mb-3">
                  <label htmlFor="cvInput" className="form-label">Upload CV</label>
                  <input type="file" className="form-control" id="cvInput" onChange={e => setCv(e.target.files[0])} required />
                </div>

                {isPending ? <button type="submit" className="btn btn-secondary" disabled>Adding {entity}...</button> :
                <button type="submit" className="btn btn-primary">Add {entity}</button>}

            </form>
            </div>
        </div>
        </>
     );
}
 
export default StudentAdd;