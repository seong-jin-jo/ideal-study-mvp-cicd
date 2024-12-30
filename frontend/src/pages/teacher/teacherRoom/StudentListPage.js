import React, { useEffect, useState } from "react";
import { readStudentsByTeacher } from "../../../services/user/UserService.mjs";

const StudentListPage = ({ classId }) => {
  const [students, setStudents] = useState([]);
  useEffect(() => {
    const fetchStudents = async () => {
      const data = await readStudentsByTeacher(classId);
      setStudents(data.students);
    };
    fetchStudents();
  }, [classId]);

  return (
    <div>
      <h3>학생 목록</h3>
      <p>자신에게 수업신청했거나, 수업중이거나, 수업완료한 학생목록을 봄</p>
      <ul>
        {students.map((student) => (
          <li key={student.id}>{student.name}</li>
        ))}
      </ul>
    </div>
  );
};
export default StudentListPage;
