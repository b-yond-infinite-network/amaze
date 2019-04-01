import User from "../entity/User";

export default class UserUtil {
    public static toJson(user: User): object {
        return {
            description: user.description,
            email: user.email,
            id: user.id,
            name: user.name,
        };
    }

    public static toUser(obj: any): User {
        const {name, email, description} = obj;
        const user = new User();
        user.name = name;
        user.email = email;
        user.description = description;
        return user;
    }

    public static isValid(user: User): boolean {
        return true; // TODO: implement user validation.
    }
}