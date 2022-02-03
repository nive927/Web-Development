const questions = [
    {
        id: 1,
        questionText: 'Everything in React is a ______________',
        answerOptions: [
            { answerText: 'Module', isCorrect: false },
            { answerText: 'Component', isCorrect: true },
            { answerText: 'Package', isCorrect: false },
            { answerText: 'Class', isCorrect: false },
        ],
    },
    {
        id: 2,
        questionText: 'In which directory React Components are saved?',
        answerOptions: [
            { answerText: 'Inside js/components/', isCorrect: true },
            { answerText: 'Inside vendor/components/', isCorrect: false },
            { answerText: 'Inside external/components/', isCorrect: false },
            { answerText: 'Inside vendor/', isCorrect: false },
        ],
    },
    {
        id: 3,
        questionText: 'How many elementsdoes a react Component return?',
        answerOptions: [
            { answerText: 'Multiple', isCorrect: true },
            { answerText: 'One', isCorrect: false },
            { answerText: 'Two', isCorrect: false },
            { answerText: 'None', isCorrect: false },
        ],
    },
    {
        id: 4,
        questionText: 'What is Babel?',
        answerOptions: [
            { answerText: 'A transpiler', isCorrect: false },
            { answerText: 'An interpreter', isCorrect: false },
            { answerText: 'A Compiler', isCorrect: false },
            { answerText: 'Both Compiler and Transpiler', isCorrect: true },
        ],
    },
];

export default questions