import { useEffect, useState } from 'react'

function Profile() {
    const [profile, setProfile] = useState(null)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState('')

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const token = localStorage.getItem('token')

                const response = await fetch(
                    'http://localhost:8080/api/profile',
                    {
                        method: 'GET',
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                )

                if (!response.ok) {
                    throw new Error('Failed to fetch profile')
                }

                const data = await response.json()

                setProfile(data)
            } catch (error) {
                setError('Unable to load profile.')
            } finally {
                setLoading(false)
            }
        }

        fetchProfile()
    }, [])

    if (loading) {
        return (
            <div className="min-h-screen bg-gray-100 p-8">
                <p className="text-gray-500">
                    Loading profile...
                </p>
            </div>
        )
    }

    if (error) {
        return (
            <div className="min-h-screen bg-gray-100 p-8">
                <p className="text-red-600">
                    {error}
                </p>
            </div>
        )
    }

    return (
        <div className="min-h-screen bg-gray-100 p-8">

            <div>
                <h1 className="text-3xl font-bold text-gray-800">
                    My Profile
                </h1>

                <p className="mt-2 text-gray-600">
                    View your account information.
                </p>
            </div>

            {profile && (
                <div className="mt-8 max-w-2xl bg-white rounded-xl shadow p-8">

                    <div className="space-y-6">

                        <div>
                            <p className="text-sm text-gray-500">
                                User ID
                            </p>
                            <p className="mt-1 text-lg font-semibold text-gray-800">
                                {profile.id}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm text-gray-500">
                                Username
                            </p>
                            <p className="mt-1 text-lg font-semibold text-gray-800">
                                {profile.username}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm text-gray-500">
                                Email
                            </p>
                            <p className="mt-1 text-lg text-gray-700">
                                {profile.email}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm text-gray-500">
                                Role
                            </p>
                            <p className="mt-1 text-lg font-semibold text-gray-800">
                                {profile.role}
                            </p>
                        </div>

                    </div>

                </div>
            )}
        </div>
    )
}

export default Profile