import React, { useState } from "react";
import {
  createClass,
  updateClass,
} from "../../services/classroom/ClassroomService.mjs";
import styles from "./ClassroomForm.module.css";
import Button from "../Button";

const ClassroomForm = ({ initialData = null, onSubmit }) => {
  const [form, setForm] = useState(
    initialData || { title: "", description: "" }
  );

  const handleSubmit = async (e) => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <div className={styles.imageContainer}>
        <img src={form.thumbnail} alt="썸네일 이미지" />
      </div>
      <div className={styles.inputContainer}>
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
      <Button type="submit">{form.id ? "수정하기" : "생성하기"}</Button>
    </form>
  );
};

export default ClassroomForm;
