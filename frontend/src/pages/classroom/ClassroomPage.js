import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import { readClassById } from '../../services/ClassroomService.mjs';
import LikeButton from '../../components/LikeButton';

const ClassroomPage = () => {
  const { classId } = useParams();
  const [classroom, setClassroom] = useState(null);

  useEffect(() => {
    // API 호출로 클래스 상세 정보 가져오기
    const fetchClass = async () => {
      const data = await readClassById(classId);
      setClassroom(data);
    };
    fetchClass();
  }, [classId]);

  if (!classroom) return <p>로딩 중...</p>;

  return (
    <div>
      <h1>{classroom.title}</h1>
      <p>{classroom.description}</p>
      <LikeButton />
    </div>
  );
};

export default ClassroomPage;
