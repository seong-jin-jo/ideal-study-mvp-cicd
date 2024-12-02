import React, { useState, useEffect } from 'react';
import { getInquiriesByClassId } from '../../../services/classroom/InquiryService.mjs';

const InquiyBoard = ({ classId }) => {
  const [inquiries, setInquiries] = useState([]);

  useEffect(() => {
    // 클래스별 문의 목록 가져오기
    const fetchClasses = async () => {
      const data = await getInquiriesByClassId();
      setInquiries(data);
    };
    fetchClasses();
  },[])

  return (
    <div className="inquiry-board">
      <h2>문의 게시판</h2>
      {/* 문의 리스트 추가 */}
      <p>문의 글들이 여기에 표시됩니다.</p>
      <table>
        <thead>
          <tr>
            <th>번호</th>
            <th>글 제목</th>
            <th>작성자</th>
            <th>공개 여부</th>
            <th>댓글 개수</th>
          </tr>
        </thead>
        <tbody>
          {inquiries.map(inquiry =>(
            <tr key={inquiry.id}>
              <td>{inquiry.id}</td>
              <td>{inquiry.title}</td>
              <td>{inquiry.author}</td>
              <td>{inquiry.isPublic ? "공개" : "비공개"}</td>
              <td>{inquiry.comments}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default InquiyBoard;