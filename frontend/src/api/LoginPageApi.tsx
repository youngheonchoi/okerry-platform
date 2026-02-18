export async function actionLogin(id: string, password: String) {
    const res = await fetch("http://localhost:8080/auth/actionLogin", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ id, password }),
    });
}
