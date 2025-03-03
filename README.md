# OpenGL Practice Code

<p>Cube Vertices and Faces</p>

```
float vertices[] = {
  -0.5f, 0.5f, 0.5f, // 0
  0.5f, 0.5f, 0.5f, // 1
  0.5f, -0.5f, 0.5f, // 2
  -0.5f, -0.5f, 0.5f, // 3

  -0.5f, 0.5f, -0.5f, // 4
  0.5f, 0.5f, -0.5f, // 5
  0.5f, -0.5f, -0.5f, // 6
  -0.5f, -0.5f, -0.5f, // 7
};
```

```
short faces[] = {
  // front face
  0, 1, 2,
  2, 3, 0,
  // back face
  4, 5, 6,
  6, 7, 4,
  // right face
  1, 5, 6,
  6, 2, 1,
  // left face
  0, 4, 7,
  7, 3, 0,
  // top face
  4, 5, 1,
  1, 0, 4,
  // bottom face
  7, 6, 2,
  2, 3, 7
};
```
