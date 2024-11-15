// 방명록
import React, {useState} from 'react';
import Button from '../Button';
import { updateBio } from '../../services/MyPageService.mjs';

const Bio = ({user, fetchedBio, isAuthenticated}) => {

    const [bio, setBio] = useState(fetchedBio?.bio || '');
    const [isEditing, setIsEditing] = useState(false);  // 수정 모드 상태

    const handleEditClick = () => {
        setIsEditing(true);  // 수정 모드 활성화
      };
    
    const handleChange = (e) => {
    setBio(e.target.value);  // 입력값 상태 업데이트
    };

    const handleSaveClick = async () => {
        const updatedBio = await updateBio(user.userId, bio);
        setBio(updatedBio.bio);  // 업데이트된 자기소개 내용 반영
        setIsEditing(false);  // 수정 모드 종료
      };

    return (
        <div>
            <h3>자기소개</h3> 
            {console.log("[Bio 컴포넌트] ",fetchedBio)}   
            <div>
                {isEditing ? (
                <textarea 
                    value={bio} 
                    onChange={handleChange}
                    style={{
                        width: '100%',    // 가로 100%
                        height: '100px',  // 높이 지정
                        resize: 'vertical' // 세로 크기만 조절 가능
                    }} 
                />  // 수정 모드에서는 textarea
                ) : (
                <div>{bio || '자기소개가 없습니다.'}</div>  // 수정 모드가 아니면 자기소개 내용 표시
                )}
            </div>
            <br/>
            { isAuthenticated && 
              <>
                {isEditing ? (
                  <Button onClick={handleSaveClick}>저장</Button>  // 저장 버튼
                ) : (
                  <Button onClick={handleEditClick}>수정</Button>  // 수정 버튼
                )}
              </>
            }
        </div>
    );
};

export default Bio;