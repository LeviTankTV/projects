class PlayerRenderer {
    constructor(ctx) {
        if (!ctx) {
            throw new Error("Canvas context is undefined");
        }
        this.ctx = ctx;
        this.catEarsImage = new Image(); // Create a new Image object
        this.catEarsImage.src = './res/catEars.png'; // Set the source of the image
        this.clownhatImage = new Image();
        this.clownhatImage.src = './res/clown.png';
        this.adminHatImage = new Image();
        this.adminHatImage.src = './res/admin.png'
    }

    drawBody(x, y, size, gradient, username) {
        // Draw the body
        this.ctx.beginPath();
        this.ctx.fillStyle = 'rgba(210, 180, 100, 0.7)';
        this.ctx.arc(x, y, size + 3, 0, Math.PI * 2);
        this.ctx.fill();

        // Draw the body with gradient
        this.ctx.beginPath();
        this.ctx.fillStyle = gradient;
        this.ctx.arc(x, y, size, 0, Math.PI * 2);
        this.ctx.fill();

        if (username === "CursedZ" && this.catEarsImage.complete) {
            const earWidth = 65; // Width of the cat ears
            const earHeight = 30; // Height of the cat ears
            this.ctx.drawImage(this.catEarsImage, x - earWidth / 2, y - size - earHeight +20, earWidth, earHeight);
        }

    //    if (username === '-ru-Me-ru-' && this.clownhatImage.complete) {
    //        const earWidth = 50; // Width of the cat ears
    //        const earHeight = 30; // Height of the cat ears
    //        this.ctx.drawImage(this.clownhatImage, x - earWidth / 2, y - size - earHeight + 10, earWidth, earHeight);
    //    }

        if ((username === "LeviTank_TV" || username === "qwerty1") && this.adminHatImage.complete) {
            const hatWidth = 55; // Width of the cat ears
            const hatHeight = 45; // Height of the cat ears
            this.ctx.drawImage(this.adminHatImage, x - hatWidth / 2, y - size - hatHeight + 12, hatWidth, hatHeight);
        }
    }

// Method to draw devil horns
    drawHorns(x, y, size) {
        const hornHeight = 30; // Высота рогов
        const hornWidth = 15; // Ширина рогов
        const baseOffset = 5; // Смещение для основания рогов

        // Создаем радиальный градиент для левого рога
        const leftHornGradient = this.ctx.createRadialGradient(
            x - size / 2, y - size, 5, // Начальная точка градиента
            x - size / 2, y - size, hornWidth // Конечная точка градиента
        );
        leftHornGradient.addColorStop(0, 'rgba(255, 0, 0, 1)'); // Светлый красный
        leftHornGradient.addColorStop(1, 'rgba(150, 0, 0, 1)'); // Темный красный

        // Левый рог
        this.ctx.beginPath();
        this.ctx.moveTo(x - size / 2 - baseOffset, y - size); // Начальная точка
        this.ctx.bezierCurveTo(
            x - size / 2 - hornWidth, // Контрольная точка 1 (слева)
            y - size - hornHeight,    // Контрольная точка 1 (вверх)
            x - size / 2 - hornWidth / 2, // Контрольная точка 2 (справа)
            y - size - hornHeight / 2, // Контрольная точка 2 (вверх)
            x - size / 2,             // Конечная точка (кончик)
            y - size                   // Возврат к основанию
        );
        this.ctx.closePath();
        this.ctx.fillStyle = leftHornGradient; // Используем градиент
        this.ctx.fill();
        this.ctx.strokeStyle = 'black'; // Цвет обводки
        this.ctx.lineWidth = 2; // Толщина обводки
        this.ctx.stroke(); // Рисуем обводку

        // Создаем радиальный градиент для правого рога
        const rightHornGradient = this.ctx.createRadialGradient(
            x + size / 2, y - size, 5, // Начальная точка градиента
            x + size / 2, y - size, hornWidth // Конечная точка градиента
        );
        rightHornGradient.addColorStop(0, 'rgba(255, 0, 0, 1)'); // Светлый красный
        rightHornGradient.addColorStop(1, 'rgba(150, 0, 0, 1)'); // Темный красный

        // Правый рог
        this.ctx.beginPath();
        this.ctx.moveTo(x + size / 2 + baseOffset, y - size); // Начальная точка
        this.ctx.bezierCurveTo(
            x + size / 2 + hornWidth, // Контрольная точка 1 (справа)
            y - size - hornHeight,     // Контрольная точка 1 (вверх)
            x + size / 2 + hornWidth / 2, // Контрольная точка 2 (слева)
            y - size - hornHeight / 2, // Контрольная точка 2 (вверх)
            x + size / 2,              // Конечная точка (кончик)
            y - size                    // Возврат к основанию
        );
        this.ctx.closePath();
        this.ctx.fillStyle = rightHornGradient; // Используем градиент
        this.ctx.fill();
        this.ctx.strokeStyle = 'black'; // Цвет обводки
        this.ctx.lineWidth = 2; // Толщина обводки
        this.ctx.stroke(); // Рисуем обводку
    }

    drawEyes(x, y, emotion, keys, isDead, radius) {
        const eyeRadiusX = radius * 0.2; // Adjust eye width based on radius
        const eyeRadiusY = radius * 0.2; // Adjust eye height based on radius
        const pupilRadius = radius * 0.08; // Adjust pupil size based on radius
        const eyeSpacing = radius * 0.25; // Adjust spacing based on radius

        // Modify eye height and expression based on emotion and keys
        let eyeHeightModifier = 0.8;
        let eyeBrowOffset = 0;

        // Happy emotion (left mouse down)
        if (emotion === 'happy') {
            eyeHeightModifier = 0.75;  // Wider, more open eyes
            eyeBrowOffset = -2;       // Lifted eyebrows
        }

        // Intense emotion (shift pressed)
        if (emotion === 'intense') {
            eyeHeightModifier = 0.5;  // Narrower, more focused eyes
            eyeBrowOffset = -1;       // Slightly lowered eyebrows
        }

        // Angry emotion takes precedence
        if (emotion === 'angry') {
            eyeHeightModifier = 0.3;  // Very narrow eyes
        }

        // Determine look direction
        let lookDirectionX = 0;
        let lookDirectionY = 0;

        if (keys.a) lookDirectionX = -1;
        if (keys.d) lookDirectionX = 1;
        if (keys.w) lookDirectionY = -1;
        if (keys.s) lookDirectionY = 1;

        const maxPupilOffsetX = eyeRadiusX - pupilRadius;
        const maxPupilOffsetY = (eyeRadiusY * eyeHeightModifier) - pupilRadius;

        const drawEye = (eyeX) => {
            this.ctx.save();

            // Eyebrow
            this.ctx.beginPath();
            this.ctx.moveTo(eyeX - eyeRadiusX, y - 6 + eyeBrowOffset);
            this.ctx.lineTo(eyeX + eyeRadiusX, y - 6 + eyeBrowOffset);
            this.ctx.strokeStyle = 'black';
            this.ctx.lineWidth = 1;
            this.ctx.stroke();

            // Eye white
            this.ctx.fillStyle = 'white';
            this.ctx.strokeStyle = 'black';
            this.ctx.lineWidth = 1;

            this.ctx.beginPath();
            this.ctx.ellipse(
                eyeX,
                y - 6,
                eyeRadiusX,
                eyeRadiusY * eyeHeightModifier,
                0, 0, Math.PI * 2
            );
            this.ctx.fill();
            this.ctx.stroke();

            // Pupil
            this.ctx.fillStyle = 'black';
            this.ctx.beginPath();
            this.ctx.arc(
                eyeX + (lookDirectionX * maxPupilOffsetX),
                y - 6 + (lookDirectionY * maxPupilOffsetY),
                pupilRadius,
                0, Math.PI * 2
            );
            this.ctx.fill();

            this.ctx.restore();
        };


        if (isDead) {
            // Draw crossed Xs for dead eyes
            this.ctx.save();
            this.ctx.strokeStyle = 'black';
            this.ctx.lineWidth = 2;

            // Left Eye X
            this.ctx.beginPath();
            this.ctx.moveTo(x - eyeSpacing - eyeRadiusX, y - 6 - eyeRadiusY);
            this.ctx.lineTo(x - eyeSpacing + eyeRadiusX, y - 6 + eyeRadiusY);
            this.ctx.moveTo(x - eyeSpacing + eyeRadiusX, y - 6 - eyeRadiusY);
            this.ctx.lineTo(x - eyeSpacing - eyeRadiusX, y - 6 + eyeRadiusY);
            this.ctx.stroke();

            // Right Eye X
            this.ctx.beginPath();
            this.ctx.moveTo(x + eyeSpacing - eyeRadiusX, y - 6 - eyeRadiusY);
            this.ctx.lineTo(x + eyeSpacing + eyeRadiusX, y - 6 + eyeRadiusY);
            this.ctx.moveTo(x + eyeSpacing + eyeRadiusX, y - 6 - eyeRadiusY);
            this.ctx.lineTo(x + eyeSpacing - eyeRadiusX, y - 6 + eyeRadiusY);
            this.ctx.stroke();

            this.ctx.restore();
        } else {
            drawEye(x - eyeSpacing);
            drawEye(x + eyeSpacing);
        }
    }

    drawMouth(x, y, emotion, keys, isDead, radius) {
        this.ctx.strokeStyle = 'black';
        this.ctx.lineWidth = 2;

        this.ctx.save();
        this.ctx.translate(x, y + radius * 0.35);

        if (isDead) {
            // Draw a straight line for the mouth when dead
            this.ctx.beginPath();
            this.ctx.moveTo(-5, 0);
            this.ctx.lineTo(5, 0);
            this.ctx.stroke();
        } else {
            const mouthWidth = radius * 0.25; // Adjust mouth width based on radius
            const mouthHeight = radius * 0.2; // Adjust mouth height based on radius
            let mouthCurve = 1;

            // Happy emotion (left mouse down) - wide, exaggerated smile
            if (emotion === 'happy') {
                mouthCurve = 1;  // Pronounced upward curve
                this.ctx.lineWidth = 2;  // Slightly thicker line
            }

            // Intense emotion (shift pressed) - tight, serious mouth
            if (emotion === 'intense') {
                mouthCurve = -1.5;  // Downward, tense curve
                this.ctx.lineWidth = 2.5;  // Slightly thicker line
            }

            // Existing emotion-based modifications
            switch (emotion) {
                case 'angry':
                    mouthCurve = -1;
                    break;
                case 'sad':
                    mouthCurve = -0.5;
                    break;
            }

            this.ctx.beginPath();
            this.ctx.moveTo(-mouthWidth, 0);
            this.ctx.quadraticCurveTo(
                0,
                mouthCurve * mouthHeight,
                mouthWidth, 0
            );

            this.ctx.stroke();
        }

        this.ctx.restore();
    }

    createBodyGradient(ctx, x, y, size) {
        if (!ctx) {
            console.error('Context is undefined in createBodyGradient');
            return 'yellow'; // Возвращаем резервный цвет
        }

        const gradient = ctx.createRadialGradient(
            x, y, 10,
            x, y, size
        );

        gradient.addColorStop(0, '#FFEC8B');
        gradient.addColorStop(1, '#FFD700');

        return gradient;
    }

    drawDoll(x, y, radius, emotion, playerName, isDead) {
        // Create a gradient for the doll body
        const gradient = this.createBodyGradient(this.ctx, x, y, radius);

        // Draw the doll's body
        this.drawBody(x, y, radius, gradient, playerName); // Use the same method to draw the body

        // Draw the doll's eyes and mouth based on emotion
        this.drawEyes(x, y, emotion, { x: 0, y: 0 }, isDead, radius); // No movement for the doll
        this.drawMouth(x, y, emotion, { x: 0, y: 0 }, isDead, radius); // No movement for the doll
    }
}
