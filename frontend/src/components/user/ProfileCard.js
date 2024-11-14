// 회원 목록에서 보이는 회원프로필카드
import React from 'react';

const ProfileCard = ({ user }) => (
  <div className="profile-card">
    <img src={user.avatar} alt={`${user.name} 프로필`} />
    <h3>{user.name}</h3>
    <p>Email: {user.email}</p>
    <p>Location: {user.location}</p>
  </div>
);

export default ProfileCard;
