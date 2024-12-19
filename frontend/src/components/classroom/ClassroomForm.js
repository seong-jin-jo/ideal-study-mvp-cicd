import React, { useState } from "react";
import {
  createClass,
  updateClass,
} from "../../services/classroom/ClassroomService.mjs";
import styles from "./ClassroomForm.module.css";

const ClassroomForm = ({ initialData = null, onSuccess }) => {
  const [form, setForm] = useState(
    initialData || { title: "", description: "" }
  );

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (form.id) {
      await updateClass(form.id, form);
    } else {
      await createClass(form);
    }
    onSuccess();
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className={styles.imageContainer}>
        <img src={form.thumbnail} alt="썸네일 이미지" />
      </div>
      <div className={styles.formContainer}>
        <h2>클래스 제목</h2>
        <input
          type="text"
          placeholder="클래스 제목"
          value={form.title}
          onChange={(e) => setForm({ ...form, title: e.target.value })}
        />
        <h2>클래스 설명</h2>
        <textarea
          placeholder="클래스 설명"
          value={form.description}
          onChange={(e) => setForm({ ...form, description: e.target.value })}
        />
      </div>
      <button type="submit">{form.id ? "수정하기" : "생성하기"}</button>
    </form>
  );
};

export default ClassroomForm;
