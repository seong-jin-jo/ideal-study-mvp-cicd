import React, { useState, useEffect, useContext }from 'react';
import { createEnrollment, readEnrollment } from '../../../services/classroom/EnrollmentService.mjs';
import { AuthContext } from '../../../context/AuthContext';
import Button from '../../Button';


const EnrollmentForm = ({ classId, isClose }) => {
  const { userInfo } = useContext(AuthContext);

  const [formData, setFormData] = useState({
    currentGrade: '',
    targetGrade: '',
    concerns: '',
    goals: '',
    requests: '',
  });

  // 수업신청이력이 있다면 정보가져오기
  useEffect(()=>{
    const fetchEnrollment = async () =>{
      const data = await readEnrollment({classId, userInfo});
      setFormData(data);
    }
    fetchEnrollment();
  },[classId])

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData( (prev) => ({
      ...prev,
      [name]: value
    }) );
  }

  const handleSubmit = async(e) => {
    e.preventDefault();
    await createEnrollment(formData);
    alert("수업신청이 완료되었습니다");
    isClose();
  }

  return (
    <div className="enrollment-form">
      <h3>수업 신청</h3>
      <form onSubmit={handleSubmit}>
        <div>
          <label>현재 성적</label>
          <input
            type="text"
            name="currentGrade"
            value={formData.currentGrade}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>목표 성적</label>
          <input
            type="text"
            name="targetGrade"
            value={formData.targetGrade}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>고민되는 점</label>
          <textarea
            name="concerns"
            value={formData.concerns}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>수업에서 얻고 싶은 것</label>
          <textarea
            name="goals"
            value={formData.goals}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>요청사항 및 한마디</label>
          <textarea
            name="requests"
            value={formData.requests}
            onChange={handleChange}
          />
        </div>
        <br></br>
        <Button type="submit"> 신청 </Button>
        <Button onClick={isClose} > 나중에 </Button>
      </form>
    </div>
  );
};

export default EnrollmentForm;