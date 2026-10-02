import { useState } from 'react'

function Register() {
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  const handleRegister = async (event) => {
    event.preventDefault()

    try {
      const response = await fetch(
          'http://localhost:8080/api/auth/register',
          {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              username,
              email,
              password,
            }),
          }
      )

      if (!response.ok) {
        throw new Error('Registration failed')
      }

      alert('Registration successful! You can now login.')

      window.location.href = '/login'
    } catch (error) {
      alert('Registration failed. Please check your details.')
    }
  }

  return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center px-4">
        <div className="w-full max-w-md bg-white rounded-xl shadow-lg p-8">

          <div className="text-center">
            <h1 className="text-3xl font-bold text-gray-800">
              Log Sentinel
            </h1>

            <p className="mt-2 text-gray-500">
              Create your account
            </p>
          </div>

          <form
              onSubmit={handleRegister}
              className="mt-8 space-y-5"
          >

            <div>
              <label className="block text-sm font-medium text-gray-700">
                Username
              </label>

              <input
                  type="text"
                  value={username}
                  onChange={(event) => setUsername(event.target.value)}
                  placeholder="Enter your username"
                  className="mt-2 w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">
                Email
              </label>

              <input
                  type="email"
                  value={email}
                  onChange={(event) => setEmail(event.target.value)}
                  placeholder="Enter your email"
                  className="mt-2 w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">
                Password
              </label>

              <input
                  type="password"
                  value={password}
                  onChange={(event) => setPassword(event.target.value)}
                  placeholder="Create a password"
                  className="mt-2 w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
              />
            </div>

            <button
                type="submit"
                className="w-full bg-blue-600 text-white py-3 rounded-lg font-medium hover:bg-blue-700"
            >
              Create Account
            </button>

          </form>

          <p className="mt-6 text-center text-sm text-gray-600">
            Already have an account?{' '}
            <a
                href="/login"
                className="text-blue-600 font-medium hover:underline"
            >
              Login
            </a>
          </p>

        </div>
      </div>
  )
}

export default Register