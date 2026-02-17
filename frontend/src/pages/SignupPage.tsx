import React, { FC, useState, ChangeEvent } from "react";
import "../assets/css/auth.css";
import {sendEmailVerification} from "../api/SignupPageApi";

const SignupPage: FC = () => {
    const [email, setEmail] = useState<string>("");
    const [verificationCode, setVerificationCode] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [showVerification, setShowVerification] = useState<boolean>(false);

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    const handleVerifyClick = async (): Promise<void> => {
        if(!email){
            alert("이메일을 입력해주세요.");
            return;
        }

        if (!emailRegex.test(email)) {
            alert("올바른 이메일 형식이 아닙니다.");
            return;
        }

        try {
            await sendEmailVerification(email);
            alert("입력하신 이메일로 인증 메일을 발송 했습니다.")
            setShowVerification(true);
        } catch (err) {
            alert("이메일 발송에 실패했습니다.");
            console.error(err);
        }
    };

    const handleSignup = (): void => {
        console.log("Signup:", { email, verificationCode, password });
    };

    return (
        <div className="auth-container">
            <div className="auth-box">
                <h1 className="auth-title">회원가입</h1>

                <div className="email-row">
                    <input
                        type="email"
                        placeholder="이메일"
                        className="auth-input"
                        value={email}
                        onChange={(e: ChangeEvent<HTMLInputElement>) =>
                            setEmail(e.target.value)
                        }
                    />
                    <button
                        className="verify-button"
                        onClick={handleVerifyClick}
                    >
                        인증메일발송
                    </button>
                </div>

                {showVerification && (
                    <div className="email-row">
                        <input
                            type="text"
                            placeholder="인증번호 입력"
                            className="auth-input"
                            value={verificationCode}
                            onChange={(e: ChangeEvent<HTMLInputElement>) =>
                                setVerificationCode(e.target.value)
                            }
                        />
                        <button
                            className="verify-button"
                            onClick={handleVerifyClick}
                        >
                            인증
                        </button>
                    </div>
                )}

                <input
                    type="password"
                    placeholder="비밀번호"
                    className="auth-input"
                    value={password}
                    onChange={(e: ChangeEvent<HTMLInputElement>) =>
                        setPassword(e.target.value)
                    }
                />

                <button
                    className="auth-button primary"
                    onClick={handleSignup}
                >
                    회원가입
                </button>
            </div>
        </div>
    );
};

export default SignupPage;
