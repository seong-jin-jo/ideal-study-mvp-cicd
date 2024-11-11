import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { readUser } from '../../services/auth/UserService.mjs';

const ProfilePage = () => {
    const { id } = useParams();
    const [user, setUser] = useState(null); // 유저 정보

    useEffect( ()=>{
        const fetchUserProfile = async () => {
            try {
              const data = await readUser(id);
              setUser(data);
            } catch (error) {
              console.error('Error fetching user detail:', error);
            }
          };
          fetchUserProfile();
    },[id])

    if (!user) return <p>Loading...</p>;

    // 디버깅
    console.log(id, user);

    return (
        <div className="user-profile">
        <div className="profile-header">
          <img className="profile-avatar" src={user.avatar} alt={`${user.name} 프로필`} />
          <h2>{user.name}</h2>
          <p className="profile-location">{user.location}</p>
        </div>
        <div className="profile-details">
          <p><strong>Email:</strong> {user.email}</p>
          <p><strong>Phone:</strong> {user.phone}</p>
          <p><strong>About:</strong> {user.about}</p>
        </div>
      </div>
    );
};

export default ProfilePage;