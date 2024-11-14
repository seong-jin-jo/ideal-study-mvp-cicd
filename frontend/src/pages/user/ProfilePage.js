import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import ProfileCard from '../../components/user/ProfileCard';
import { readUsers } from '../../services/auth/UserService.mjs';

const ProfilePage = () => {
  const [profiles, setProfiles] = useState([]);
  const location = useLocation();

  useEffect(() => {
    const fetchProfiles = async () => {
      try {
            const data = await readUsers(location.pathname); // 데이터 자체를 반환받음
            setProfiles(data); // 그대로 profiles에 설정
          } catch (error) {
            console.error('Error fetching profiles:', error);
          }
    };
    fetchProfiles();
  }, [location.pathname]);

  return (
    <div className="profile-page">
      <h2>{location.pathname === '/teachers' ? '선생님 목록입니다' : '학생 목록입니다'}</h2>
      <div className="profile-grid">
        {profiles.map((user) => (
          <ProfileCard key={user.id} user={user} />
        ))}
      </div>
    </div>
  );
};

export default ProfilePage;