import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import InteractiveBackground from '../components/InteractiveBackground'

function Login() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  const navigate = useNavigate()

  const handleLogin = async (event) => {
    event.preventDefault()

    try {
      const response = await fetch(
        'http://localhost:8080/api/auth/login',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            username,
            password,
          }),
        }
      )

      if (!response.ok) {
        throw new Error('Login failed')
      }

      const data = await response.json()

      localStorage.setItem('token', data.token)

      navigate('/dashboard')
    } catch (error) {
      alert('Login failed. Please check your username and password.')
    }
  }

  return (
    <div className="relative min-h-screen overflow-hidden">

      {/* Magic Background */}
      <InteractiveBackground />

      {/* Login Form */}
      <div className="relative z-10 min-h-screen flex items-center justify-center px-4">

        <div className="w-full max-w-md bg-gray-900/80 backdrop-blur-md border border-purple-500/40 rounded-2xl shadow-2xl p-8">

          {/* Header */}
          <div className="text-center">

            <h1 className="text-3xl font-bold text-white">
              Log Sentinel
            </h1>

            <p className="mt-2 text-gray-300">
              Login to your account
            </p>

          </div>

          {/* Login Form */}
          <form
            onSubmit={handleLogin}
            className="mt-8 space-y-5"
          >

            {/* Username */}
            <div>

              <label className="block text-sm font-medium text-gray-200">
                Username
              </label>

              <input
                type="text"
                value={username}
                onChange={(event) => setUsername(event.target.value)}
                placeholder="Enter your username"
                className="mt-2 w-full px-4 py-3 bg-gray-800/80 text-white placeholder-gray-400 border border-purple-500/40 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition"
                required
              />

            </div>

            {/* Password */}
            <div>

              <label className="block text-sm font-medium text-gray-200">
                Password
              </label>

              <input
                type="password"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
                placeholder="Enter your password"
                className="mt-2 w-full px-4 py-3 bg-gray-800/80 text-white placeholder-gray-400 border border-purple-500/40 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition"
                required
              />

            </div>

            {/* Login Button */}
            <button
              type="submit"
              className="w-full bg-gradient-to-r from-purple-600 to-pink-600 text-white py-3 rounded-lg font-semibold hover:from-purple-700 hover:to-pink-700 transition-all duration-300 shadow-lg"
            >
              Login
            </button>

          </form>

          {/* Register Link */}
          <p className="mt-6 text-center text-sm text-gray-300">

            Don't have an account?{' '}

            <a
              href="/register"
              className="text-purple-400 font-medium hover:text-pink-400 hover:underline"
            >
              Register
            </a>

          </p>

        </div>

      </div>

    </div>
  )
}

export default Login