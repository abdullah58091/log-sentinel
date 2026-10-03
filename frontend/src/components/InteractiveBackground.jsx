import { useEffect, useRef } from 'react'

function InteractiveBackground() {
  const canvasRef = useRef(null)

  useEffect(() => {
    const canvas = canvasRef.current
    const ctx = canvas.getContext('2d')

    let animationFrameId
    let stars = []

    const mouse = {
      x: null,
      y: null,
    }

    const config = {
      starCount: 500,
      speed: 8,
    }

    const resizeCanvas = () => {
      canvas.width = window.innerWidth
      canvas.height = window.innerHeight

      createStars()
    }

    const createStars = () => {
      stars = []

      for (let i = 0; i < config.starCount; i++) {
        stars.push({
          x: (Math.random() - 0.5) * canvas.width * 2,
          y: (Math.random() - 0.5) * canvas.height * 2,
          z: Math.random() * canvas.width,

          size: Math.random() * 1.8 + 0.4,

          color:
            Math.random() < 0.45
              ? 'blue'
              : Math.random() < 0.75
                ? 'purple'
                : 'white',
        })
      }
    }

    const handleMouseMove = (event) => {
      mouse.x = event.clientX
      mouse.y = event.clientY
    }

    const handleMouseLeave = () => {
      mouse.x = null
      mouse.y = null
    }

    const drawBackground = () => {
      const gradient = ctx.createRadialGradient(
        canvas.width / 2,
        canvas.height / 2,
        0,
        canvas.width / 2,
        canvas.height / 2,
        Math.max(canvas.width, canvas.height)
      )

      gradient.addColorStop(0, '#071f52')
      gradient.addColorStop(0.35, '#031536')
      gradient.addColorStop(0.7, '#02091c')
      gradient.addColorStop(1, '#00030a')

      ctx.fillStyle = gradient
      ctx.fillRect(
        0,
        0,
        canvas.width,
        canvas.height
      )
    }

    const drawCenterGlow = (centerX, centerY) => {
      const glow = ctx.createRadialGradient(
        centerX,
        centerY,
        0,
        centerX,
        centerY,
        220
      )

      glow.addColorStop(
        0,
        'rgba(80, 180, 255, 0.9)'
      )

      glow.addColorStop(
        0.15,
        'rgba(40, 120, 255, 0.45)'
      )

      glow.addColorStop(
        0.4,
        'rgba(120, 50, 255, 0.15)'
      )

      glow.addColorStop(
        1,
        'rgba(0, 0, 0, 0)'
      )

      ctx.fillStyle = glow

      ctx.beginPath()
      ctx.arc(
        centerX,
        centerY,
        220,
        0,
        Math.PI * 2
      )
      ctx.fill()
    }

    const drawStar = (star, centerX, centerY) => {
      const previousZ = star.z

      star.z -= config.speed

      if (star.z < 1) {
        star.z = canvas.width

        star.x =
          (Math.random() - 0.5) *
          canvas.width *
          2

        star.y =
          (Math.random() - 0.5) *
          canvas.height *
          2
      }

      let targetX = centerX
      let targetY = centerY

      if (mouse.x !== null && mouse.y !== null) {
        targetX +=
          (mouse.x - centerX) * 0.15

        targetY +=
          (mouse.y - centerY) * 0.15
      }

      const scale = 420 / star.z

      const x =
        targetX +
        star.x * scale

      const y =
        targetY +
        star.y * scale

      const previousScale =
        420 / previousZ

      const previousX =
        targetX +
        star.x * previousScale

      const previousY =
        targetY +
        star.y * previousScale

      if (
        x < -200 ||
        x > canvas.width + 200 ||
        y < -200 ||
        y > canvas.height + 200
      ) {
        return
      }

      const depth =
        1 - star.z / canvas.width

      const length =
        5 + depth * 90

      const dx = x - previousX
      const dy = y - previousY

      const distance =
        Math.sqrt(dx * dx + dy * dy) || 1

      const directionX = dx / distance
      const directionY = dy / distance

      const startX =
        x - directionX * length

      const startY =
        y - directionY * length

      let color

      if (star.color === 'blue') {
        color = `rgba(
          70,
          150,
          255,
          ${0.25 + depth * 0.75}
        )`
      } else if (star.color === 'purple') {
        color = `rgba(
          220,
          70,
          255,
          ${0.25 + depth * 0.75}
        )`
      } else {
        color = `rgba(
          240,
          250,
          255,
          ${0.3 + depth * 0.7}
        )`
      }

      // Glow
      ctx.beginPath()

      ctx.moveTo(startX, startY)
      ctx.lineTo(x, y)

      ctx.strokeStyle = color
      ctx.lineWidth =
        0.5 + depth * 3

      ctx.shadowBlur =
        5 + depth * 20

      ctx.shadowColor = color

      ctx.stroke()

      ctx.shadowBlur = 0
    }

    const animate = () => {
      drawBackground()

      let centerX =
        canvas.width / 2

      let centerY =
        canvas.height / 2

      // Mouse-controlled warp point
      if (mouse.x !== null && mouse.y !== null) {
        centerX +=
          (mouse.x - centerX) * 0.08

        centerY +=
          (mouse.y - centerY) * 0.08
      }

      drawCenterGlow(
        centerX,
        centerY
      )

      stars.forEach((star) => {
        drawStar(
          star,
          centerX,
          centerY
        )
      })

      animationFrameId =
        requestAnimationFrame(animate)
    }

    resizeCanvas()
    animate()

    window.addEventListener(
      'resize',
      resizeCanvas
    )

    window.addEventListener(
      'mousemove',
      handleMouseMove
    )

    window.addEventListener(
      'mouseleave',
      handleMouseLeave
    )

    return () => {
      cancelAnimationFrame(
        animationFrameId
      )

      window.removeEventListener(
        'resize',
        resizeCanvas
      )

      window.removeEventListener(
        'mousemove',
        handleMouseMove
      )

      window.removeEventListener(
        'mouseleave',
        handleMouseLeave
      )
    }
  }, [])

  return (
    <canvas
      ref={canvasRef}
      className="fixed inset-0 w-full h-full pointer-events-none"
    />
  )
}

export default InteractiveBackground