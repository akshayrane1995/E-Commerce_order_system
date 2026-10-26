import { useState } from 'react';
import { Link , useNavigate } from 'react-router-dom';
import '../App.css';

function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);


const handleLogin = async (event) => {
  event.preventDefault();
  setMessage('');
  setLoading(true);

  try {
    const response = await fetch(
      'http://localhost:8082/auth/login',
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      }
    );

    const data = await response.json();

    if (!response.ok) {
      throw new Error(
        data.message || 'Login failed. Check your credentials.'
      );
    }

    if (!data.jwtToken) {
      throw new Error('Login response did not contain a JWT token.');
    }

    sessionStorage.setItem('jwtToken', data.jwtToken);
    sessionStorage.setItem('userId', data.userId);
    setMessage('Login successful! JWT token received.');

    navigate('/');
  } catch (error) {
    setMessage(
      error instanceof TypeError
        ? 'Cannot connect to the backend. Check that User Service is running and CORS is configured.'
        : error.message
    );
  } finally {
    setLoading(false);
  }
};


  return (
    <div className="auth-page">
      <form className="auth-card" onSubmit={handleLogin}>
        <h2>Welcome Back!</h2>
        <p>Login to your ShopEase account</p>

        <label htmlFor="email">Email</label>
        <input
          id="email"
          type="email"
          placeholder="Enter your email"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
          required
        />

        <label htmlFor="password">Password</label>
        <input
          id="password"
          type="password"
          placeholder="Enter your password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
          required
        />

        <button type="submit" disabled={loading}>
          {loading ? 'Logging in...' : 'Login'}
        </button>

        {message && <p role="status">{message}</p>}

        <p>
          Don't have an account? <Link to="/register">Register</Link>
        </p>
      </form>
    </div>
  );
}

export default Login;
