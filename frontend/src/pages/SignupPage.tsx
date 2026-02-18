import React, { FC, useState, ChangeEvent } from "react";
import "../assets/css/auth.css";
import {sendEmailVerification, sendEmailVerification2, signUp} from "../api/SignupPageApi";
import {useNavigate} from "react-router-dom";

const SignupPage: FC = () => {
    const [email, setEmail] = useState<string>("");
    const [verificationCode, setVerificationCode] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [showVerification, setShowVerification] = useState<boolean>(false);
    const [disabled, setDisabled] = useState(false);
    const [auth, setAuth] = useState(false);

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    let navigate = useNavigate();

    const handleVerifyClick = async (): Promise<void> => {
        if(!email){
            alert("이메일을 입력해주세요.");
            return;
        }

        if (!emailRegex.test(email)) {
            alert("올바른 이메일 형식이 아닙니다.");
            return;
        }

        setDisabled(true);

        try {
            await sendEmailVerification(email);
            alert("입력하신 이메일로 인증 메일을 발송 했습니다.")
            setShowVerification(true);
        } catch (err) {
            setDisabled(false);
            alert("이메일 발송에 실패했습니다.");
            console.error(err);
        }
    };

    const handleVerifyClick2 = async (): Promise<void> => {
        if(!verificationCode){
            alert("인증번호를 입력해주세요.");
            return;
        }

        try{
            await sendEmailVerification2(email, verificationCode);
            alert("인증했습니다.")
            setAuth(true);
        }catch (err){
            alert("인증번호가 틀렸습니다.");
            setAuth(false);
        }
    }

    const handleSignup = async (): Promise<void> => {
        if(!auth){
            alert("이메일을 인증해주세요.");
            return;
        }

        if(!password){
            alert("비밀번호를 입력해주세요.");
            return;
        }

        try{
            await signUp(email, password);
            console.log("Signup:", { email, password });
            alert("환영합니다.");
        }catch(err){
            alert("회원가입에 실패했습니다.");
        }

        navigate("/login");
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
                        disabled={disabled}
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
                            onClick={handleVerifyClick2}
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
