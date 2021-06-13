# Converter 를 이용해서 암/복호화를 해보자!

> 본 예제에서는 비밀번호를 convert 하고 있지만, 실무에서는 spring security 를 사용하는 것을 추천합니다.

## 설명

1. `AttributeConverter` 를 구현하는 `EncryptConverter` 를 만들고 `@Converter` 어노테이션을 작성합니다.
    - `convertToDatabaseColumn`, `convertToEntityAttribute` 를 구현합니다.
2. 사용하려는 entity 의 필드에 `@Convert(converter = EncryptConverter.class)` 를 작성합니다.
3. 본 예제에서는 `convertToEntityAttribute` 까지 구현이 되었기에 암호화된 비밀번호가 보이지 않습니다.
    - DB 를 직접보거나 `convertToEntityAttribute` 내용을 삭제하고 보면 암호화되어 있음을 확인할 수 있습니다.
