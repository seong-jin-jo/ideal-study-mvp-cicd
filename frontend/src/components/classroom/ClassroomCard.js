import React from 'react';
import { useNavigate } from 'react-router-dom';

const ClassroomCard = ({ classroom }) => {
  const navigate = useNavigate();

  return (
    <div className="classroom-card">
      <h3>{classroom.title}</h3>
      <p>{classroom.description}</p>
      <button onClick={() => navigate(`/classes/${classroom.id}`)}>상세보기</button>
    </div>
  );
};

export default ClassroomCard;